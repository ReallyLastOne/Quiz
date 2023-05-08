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
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameSessionServiceImpl implements GameSessionService {
    private final GameSessionRepository gameSessionRepository;
    private final GameSessionCreateRequestValidator createRequestValidator;
    private final GameSessionStateValidator gameSessionStateValidator;
    private final QuestionService questionService;
    private final UserService userService;

    @Override
    public Long createSession(GameSessionCreateRequest request) {
        List<StateValidationError> errors = new ArrayList<>();

        gameSessionStateValidator.validateCreateSessionRequest(userService.getCurrentUser(), errors);
        if (!errors.isEmpty()) throw new StateValidationErrorsException(errors);

        validate(request);

        request.session().setStartDate(LocalDateTime.now());
        request.session().setUser((UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return gameSessionRepository.save(request.session()).getId();
    }

    @Override
    public Question nextQuestion() {
        List<StateValidationError> errors = new ArrayList<>();
        UserEntity currentUser = userService.getCurrentUser();

        gameSessionStateValidator.validateNextQuestionRequest(currentUser, errors);
        if (!errors.isEmpty()) throw new StateValidationErrorsException(errors);

        QuizGameSession activeSession = gameSessionRepository.findActive(currentUser.getId());
        Question randomQuestion = questionService.findRandomQuestion();
        activeSession.answer(randomQuestion, null);
        activeSession.setState(GameState.IN_PROGRESS);

        return randomQuestion;
    }

    @Override
    public Phrase nextPhrase(NextPhraseRequest request) {
        return null;
    }

    @Override
    public boolean processAnswer(QuestionAnswerRequest questionAnswer) {
        List<StateValidationError> errors = new ArrayList<>();
        UserEntity currentUser = userService.getCurrentUser();

        gameSessionStateValidator.validateQuestionAnswerRequest(currentUser, errors);
        if (!errors.isEmpty()) throw new StateValidationErrorsException(errors);

        QuizGameSession activeSession = gameSessionRepository.findActive(currentUser.getId());
        Question current = activeSession.findCurrent().get().getKey();
        boolean isCorrectAnswer = current.getCorrectAnswer().equals(questionAnswer.answer());
        activeSession.answer(current, isCorrectAnswer);

        if (activeSession.getQuestionSize() == activeSession.getQuestionsAndStatus().size())
            activeSession.setState(GameState.COMPLETED);

        return isCorrectAnswer;
    }

    private void validate(GameSessionCreateRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "GameSessionCreateRequest");
        createRequestValidator.validate(request, errors);

        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
    }
}
