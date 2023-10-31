package com.reallylastone.quiz.game.session.service;

import com.reallylastone.quiz.exercise.core.Exercise;
import com.reallylastone.quiz.exercise.core.ExerciseState;
import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseToTranslate;
import com.reallylastone.quiz.exercise.phrase.service.PhraseService;
import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.service.QuestionService;
import com.reallylastone.quiz.game.core.quiz.model.QuizGameSession;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.core.translation.model.TranslationGameSession;
import com.reallylastone.quiz.game.session.model.GameSession;
import com.reallylastone.quiz.game.session.model.GameSessionCreateRequest;
import com.reallylastone.quiz.game.session.model.GameState;
import com.reallylastone.quiz.game.session.repository.GameSessionRepository;
import com.reallylastone.quiz.game.session.validation.GameSessionCreateRequestValidator;
import com.reallylastone.quiz.game.session.validation.GameSessionStateValidator;
import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.user.service.UserService;
import com.reallylastone.quiz.util.validation.StateValidationError;
import com.reallylastone.quiz.util.validation.StateValidationErrorsException;
import com.reallylastone.quiz.util.validation.ValidationErrorsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static com.reallylastone.quiz.exercise.core.ExerciseState.NO_ANSWER;

@Service
@RequiredArgsConstructor
@Slf4j
// TODO: code is duplicated mostly for one quiz/translation game session, it would be nice to generify it
// TODO: same for other classes like GameSessionCreateRequestValidator or GameSessionRepository
public class GameSessionServiceImpl implements GameSessionService {
    private final GameSessionRepository gameSessionRepository;
    private final GameSessionCreateRequestValidator createRequestValidator;
    private final GameSessionStateValidator gameSessionStateValidator;
    private final QuestionService questionService;
    private final PhraseService phraseService;

    @Override
    public Long createSession(GameSessionCreateRequest request) {
        List<StateValidationError> errors = new ArrayList<>();

        gameSessionStateValidator.validateCreateSessionRequest(UserService.getCurrentUser(), errors);
        if (!errors.isEmpty())
            throw new StateValidationErrorsException(errors);

        validate(request);

        request.session().setStartDate(LocalDateTime.now());
        UserEntity principal = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.session().setUser(principal);

        log.info("Creating game session for user: %s".formatted(principal.getNickname()));

        return gameSessionRepository.save(request.session()).getId();
    }

    @Override
    @Transactional
    public Question nextQuestion() {
        List<StateValidationError> errors = new ArrayList<>();
        UserEntity currentUser = UserService.getCurrentUser();

        gameSessionStateValidator.validateNextQuestionRequest(currentUser, errors);
        if (!errors.isEmpty())
            throw new StateValidationErrorsException(errors);

        QuizGameSession activeSession = findActive(QuizGameSession.class).get();
        Question randomQuestion = questionService.findRandomQuestion(
                activeSession.getQuestionsAndStatus().keySet().stream().map(Exercise::getId).toList());
        activeSession.answer(randomQuestion, NO_ANSWER);
        activeSession.setState(GameState.IN_PROGRESS);
        log.info("Question with id: %s drawn for session with id: %s".formatted(randomQuestion.getId(),
                activeSession.getId()));

        return randomQuestion;
    }

    @Override
    @Transactional
    public PhraseToTranslate nextPhrase() {
        List<StateValidationError> errors = new ArrayList<>();
        UserEntity currentUser = UserService.getCurrentUser();

        gameSessionStateValidator.validateNextPhraseRequest(currentUser, errors);
        if (!errors.isEmpty())
            throw new StateValidationErrorsException(errors);

        TranslationGameSession activeSession = findActive(TranslationGameSession.class).get();
        Locale sourceLanguage = activeSession.getSourceLanguage();
        Phrase randomPhrase = phraseService.findRandomPhrase(sourceLanguage, activeSession.getDestinationLanguage(),
                currentUser.getId());
        activeSession.answer(randomPhrase, NO_ANSWER);
        activeSession.setState(GameState.IN_PROGRESS);
        log.info("Phrase with id: %s drawn for session with id: %s".formatted(randomPhrase.getId(),
                activeSession.getId()));

        return new PhraseToTranslate(randomPhrase.getTranslationMap().get(sourceLanguage), sourceLanguage,
                activeSession.getDestinationLanguage());
    }

    @Override
    @Transactional
    public boolean processAnswer(QuestionAnswerRequest questionAnswer) {
        List<StateValidationError> errors = new ArrayList<>();
        UserEntity currentUser = UserService.getCurrentUser();

        gameSessionStateValidator.validateQuestionAnswerRequest(currentUser, errors);
        if (!errors.isEmpty())
            throw new StateValidationErrorsException(errors);

        QuizGameSession activeSession = findActive(QuizGameSession.class).get();
        Optional<Map.Entry<Question, ExerciseState>> currentOptional = activeSession.findCurrent();

        if (currentOptional.isEmpty()) {
            log.error(
                    "Game session with id: %s has no question that can be answered, but somehow it got through validation!"
                            .formatted(activeSession.getId()));
            throw new IllegalStateException(
                    "Trying to process the answer for the game session, which has no unanswered questions");
        }

        Question current = currentOptional.get().getKey();
        boolean isCorrectAnswer = current.isCorrect(questionAnswer.answer());
        log.info("Question with id: %s for game session with id: %s answered %s".formatted(current.getId(),
                activeSession.getId(), isCorrectAnswer ? "correctly" : "wrongly"));
        activeSession.answer(current, ExerciseState.from(isCorrectAnswer));

        if (activeSession.isLastQuestion()) {
            log.info("Last question for game session with id: %s".formatted(activeSession.getId()));
            activeSession.finish();
        }

        return isCorrectAnswer;
    }

    @Override
    @Transactional
    public boolean processAnswer(PhraseAnswerRequest phraseAnswer) {
        List<StateValidationError> errors = new ArrayList<>();
        UserEntity currentUser = UserService.getCurrentUser();

        gameSessionStateValidator.validatePhraseAnswerRequest(currentUser, errors);
        if (!errors.isEmpty())
            throw new StateValidationErrorsException(errors);

        TranslationGameSession activeSession = findActive(TranslationGameSession.class).get();
        Optional<Map.Entry<Phrase, ExerciseState>> currentOptional = activeSession.findCurrent();

        if (currentOptional.isEmpty()) {
            log.error(
                    "Game session with id: %s has no phrases that can be translated, but somehow it got through validation!"
                            .formatted(activeSession.getId()));
            throw new IllegalStateException(
                    "Trying to process the phrase for the game session, which has no unanswered phrases");
        }

        Phrase current = currentOptional.get().getKey();
        boolean isCorrectAnswer = current.isCorrect(phraseAnswer.translation(), activeSession.getDestinationLanguage());
        activeSession.answer(current, ExerciseState.from(isCorrectAnswer));

        if (activeSession.isLastPhrase()) {
            log.info("Last phrase for game session with id: %s".formatted(activeSession.getId()));
            activeSession.finish();
        }

        return isCorrectAnswer;
    }

    @Override
    @Transactional
    public void stopGame() {
        UserEntity currentUser = UserService.getCurrentUser();

        GameSession activeSession = gameSessionRepository.findActive(currentUser.getId());
        log.info("Stopping game session: %s for user with id: %s".formatted(activeSession, currentUser.getId()));
        if (activeSession != null) {
            activeSession.finish();
        }
    }

    @Override
    public <T extends GameSession> Optional<T> findActive(Class<T> gameSessionType) {
        Long id = UserService.getCurrentUser().getId();

        if (gameSessionType == QuizGameSession.class) {
            return (Optional<T>) Optional.ofNullable(gameSessionRepository.findActiveQuizGameSession(id));
        } else if (gameSessionType == TranslationGameSession.class) {
            return (Optional<T>) Optional.ofNullable(gameSessionRepository.findActiveTranslationGameSession(id));
        }

        return Optional.empty();
    }

    private void validate(GameSessionCreateRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "GameSessionCreateRequest");
        createRequestValidator.validate(request, errors);

        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
    }

    @Override
    public <T extends GameSession> List<T> findRecent(int games, Class<T> gameSessionType) {
        Long id = UserService.getCurrentUser().getId();

        if (gameSessionType == QuizGameSession.class) {
            return (List<T>) gameSessionRepository.findMostRecentQuizGameSessions(games, id);
        } else if (gameSessionType == TranslationGameSession.class) {
            return (List<T>) gameSessionRepository.findMostRecentTranslationGameSessions(games, id);
        }

        return new ArrayList<>();
    }

}
