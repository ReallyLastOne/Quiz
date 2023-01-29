package com.reallylastone.quiz.exercise.translation.validation;

import com.reallylastone.quiz.exercise.translation.model.Translation;
import com.reallylastone.quiz.exercise.translation.repository.TranslationRepository;
import com.reallylastone.quiz.util.ValidationUtils;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslationValidator {
    private final TranslationRepository translationRepository;

    public void validate(Translation translation, List<Translation> translations) {
        if (translations.size() >= 2)
            throw new ConstraintViolationException(ValidationUtils.createConstraintViolationSet("translationMap",
                    "translationMap must have unique entries " + translation.getId()));
    }
}
