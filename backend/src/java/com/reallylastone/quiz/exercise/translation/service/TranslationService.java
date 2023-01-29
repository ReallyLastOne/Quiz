package com.reallylastone.quiz.exercise.translation.service;

import com.reallylastone.quiz.exercise.translation.model.Translation;

import java.util.Optional;

public interface TranslationService {
    Optional<Translation> findById(Long id);

    Translation createTranslation(Translation translation);
}
