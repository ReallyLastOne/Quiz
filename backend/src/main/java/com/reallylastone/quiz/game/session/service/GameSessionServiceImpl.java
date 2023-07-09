package com.reallylastone.quiz.game.session.service;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.service.QuestionService;
import com.reallylastone.quiz.game.core.quiz.model.QuizGameSession;
import com.reallylastone.quiz.game.session.model.GameSessionCreateRequest;
import com.reallylastone.quiz.game.session.model.GameState;
import com.reallylastone.quiz.game.session.model.NextPhraseRequest;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameSessionServiceImpl implements GameSessionService {
    private final GameSessionRepository gameSessionRepository;
    private final GameSessionCreateRequestValidator createRequestValidator;
    private final GameSessionStateValidator gameSessionStateValidator;
    private final QuestionService questionService;

    @Override
    public Long createSession(GameSessionCreateRequest request) {
        List<StateValidationError> errors = new ArrayList<>();

        gameSessionStateValidator.validateCreateSessionRequest(UserService.getCurrentUser(), errors);
        if (!errors.isEmpty()) throw new StateValidationErrorsException(errors);

        validate(request);

        request.session().setStartDate(LocalDateTime.now());
        request.session().setUser((UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return gameSessionRepository.save(request.session()).getId();
    }

    @Override
    @Transactional
    public Question nextQuestion() {
        List<StateValidationError> errors = new ArrayList<>();
        UserEntity currentUser = UserService.getCurrentUser();

        gameSessionStateValidator.validateNextQuestionRequest(currentUser, errors);
        if (!errors.isEmpty()) throw new StateValidationErrorsException(errors);

        QuizGameSession activeSession = gameSessionRepository.findActive(currentUser.getId());
        Question randomQuestion = questionService.findRandomQuestion();
        activeSession.answer(randomQuestion, null);
        activeSession.setState(GameState.IN_PROGRESS);

        return randomQuestion;
    }

    @Override
    @Transactional
    public Phrase nextPhrase(NextPhraseRequest request) {
        return null;
    }

    @Override
    @Transactional
    public boolean processAnswer(QuestionAnswerRequest questionAnswer) {
        List<StateValidationError> errors = new ArrayList<>();
        UserEntity currentUser = UserService.getCurrentUser();

        gameSessionStateValidator.validateQuestionAnswerRequest(currentUser, errors);
        if (!errors.isEmpty()) throw new StateValidationErrorsException(errors);

        QuizGameSession activeSession = gameSessionRepository.findActive(currentUser.getId());
        Optional<Map.Entry<Question, Boolean>> currentOptional = activeSession.findCurrent();

        if (currentOptional.isEmpty()) {
            throw new IllegalStateException("Trying to process the answer for the game session, which has no unanswered questions");
        }

        Question current = currentOptional.get().getKey();
        boolean isCorrectAnswer = current.isCorrect(questionAnswer.answer());
        activeSession.answer(current, isCorrectAnswer);

        if (activeSession.isLastQuestion()) {
            activeSession.finish();
        }

        return isCorrectAnswer;
    }

    @Override
    public void stopGame() {
        UserEntity currentUser = UserService.getCurrentUser();

        QuizGameSession activeSession = gameSessionRepository.findActive(currentUser.getId());
        if (activeSession != null) {
            activeSession.finish();
        }
    }

    private void validate(GameSessionCreateRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "GameSessionCreateRequest");
        createRequestValidator.validate(request, errors);

        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
    }
}
