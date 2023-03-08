package com.reallylastone.quiz.game.session.validation;

import com.reallylastone.quiz.game.session.repository.GameSessionRepository;
import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.util.validation.StateValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class is responsible for verifying if authenticated user is in correct state to process the given request
 */
@Service
@RequiredArgsConstructor
public class GameSessionStateValidator {
    private final GameSessionRepository gameSessionRepository;

    /**
     * Method validates if given user has correct properties to start a new game session
     *
     * @param user   user to be checked
     * @param errors errors to be optionally filled
     */
    public void validateCreateSessionRequest(UserEntity user, List<StateValidationError> errors) {
        if (gameSessionRepository.hasActiveSession(user.getId())) {
            errors.add(StateValidationError.USER_ACTIVE_SESSION);
        }
    }

    /**
     * Method validates if given user has correct properties to be given next question
     *
     * @param user   user to be checked
     * @param errors errors to be optionally filled
     */
    public void validateNextQuestionRequest(UserEntity user, List<StateValidationError> errors) {
        if (!gameSessionRepository.hasActiveQuizSession(user.getId())) {
            errors.add(StateValidationError.USER_INACTIVE_SESSION);
        } else if (gameSessionRepository.hasUnansweredQuestion(user.getId())) {
            errors.add(StateValidationError.USER_UNANSWERED_QUESTIONS);
        }
    }
}
