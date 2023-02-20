package com.reallylastone.quiz.game.session.validation;

import com.reallylastone.quiz.game.core.quiz.model.QuizGameSession;
import com.reallylastone.quiz.game.core.translation.model.TranslationGameSession;
import com.reallylastone.quiz.game.session.model.GameSession;
import com.reallylastone.quiz.game.session.repository.GameSessionRepository;
import com.reallylastone.quiz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GameSessionValidator implements Validator {
    private final UserRepository userRepository;
    private final GameSessionRepository gameSessionRepository;
    private final SpringValidatorAdapter validatorAdapter;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return GameSession.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        GameSession session = (GameSession) target;

        if (validatorAdapter != null) {
            validatorAdapter.validate(target, errors);
        }

        if (session.getStartDate() == null) {
            errors.rejectValue("startDate", "invalid.startDate.value", "startDate must not be null");
        } else if (session.getStartDate().isAfter(LocalDateTime.now())) {
            errors.rejectValue("startDate", "invalid.startDate.value", "startDate must not be future date");
        }

        validateSubclass(session, errors);

        // to prevent calling database when there are already errors
        if (!errors.hasErrors()) {
            if (session.getUser() == null) {
                errors.rejectValue("user", "invalid.user.value", "user must not be null");
            } else if (!userRepository.existsById(session.getUser().getId())) {
                errors.rejectValue("user", "invalid.user.value", "user does not exists");
            } else if (gameSessionRepository.hasActiveSession(session.getUser().getId())) {
                errors.rejectValue("user", "invalid.user.value", "user already in game");
            }
        }
    }

    private void validateSubclass(GameSession session, Errors errors) {
        if (session instanceof TranslationGameSession s) {
            if (s.getSourceLanguage() == null)
                errors.rejectValue("sourceLanguage", "invalid.sourceLanguage.value", "sourceLanguage must not be null");

            if (s.getDestinationLanguage() == null)
                errors.rejectValue("destinationLanguage", "invalid.destinationLanguage.value", "destinationLanguage must not be null");
        }


        if (session instanceof QuizGameSession s) {
            if (s.getQuestionSize() <= 0)
                errors.rejectValue("questionSize", "invalid.questionSize.value", "questionSize must be positive value");
        }
    }
}
