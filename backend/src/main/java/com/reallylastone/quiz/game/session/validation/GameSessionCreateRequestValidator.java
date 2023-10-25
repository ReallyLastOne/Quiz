package com.reallylastone.quiz.game.session.validation;

import com.reallylastone.quiz.game.core.quiz.model.QuizGameSession;
import com.reallylastone.quiz.game.core.translation.model.TranslationGameSession;
import com.reallylastone.quiz.game.session.model.GameSession;
import com.reallylastone.quiz.game.session.model.GameSessionCreateRequest;
import com.reallylastone.quiz.game.session.repository.GameSessionRepository;
import com.reallylastone.quiz.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@Service
@RequiredArgsConstructor
public class GameSessionCreateRequestValidator implements Validator {
    private final UserService userService;
    private final GameSessionRepository gameSessionRepository;
    private final SpringValidatorAdapter validatorAdapter;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return GameSessionCreateRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        GameSessionCreateRequest request = (GameSessionCreateRequest) target;
        GameSession session = request.session();

        if (validatorAdapter != null) {
            validatorAdapter.validate(target, errors);
        }

        validateSubclass(session, errors);
    }

    private void validateSubclass(GameSession session, Errors errors) {
        if (session instanceof TranslationGameSession s) {
            if (s.getSourceLanguage() == null)
                errors.rejectValue("session.sourceLanguage", "invalid.sourceLanguage.value",
                        "sourceLanguage must not be null");

            if (s.getDestinationLanguage() == null)
                errors.rejectValue("session.destinationLanguage", "invalid.destinationLanguage.value",
                        "destinationLanguage must not be null");

            if (s.getPhrasesSize() <= 0)
                errors.rejectValue("session.phrasesSize", "invalid.phrasesSize.value",
                        "phrasesSize must be positive value");
            if (s.getPhrasesSize() > 15)
                errors.rejectValue("session.phrasesSize", "invalid.phrasesSize.value",
                        "phrasesSize must not be greater than 15");
        }

        if (session instanceof QuizGameSession s) {
            if (s.getQuestionSize() <= 0)
                errors.rejectValue("session.questionSize", "invalid.questionSize.value",
                        "questionSize must be positive value");
            if (s.getQuestionSize() > 15)
                errors.rejectValue("session.questionSize", "invalid.questionSize.value",
                        "questionSize must not be greater than 15");
        }
    }
}
