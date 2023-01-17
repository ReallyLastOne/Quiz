package com.reallylastone.quiz.exercise.translation.service;

import com.reallylastone.quiz.exercise.translation.model.TranslationExercise;

import java.util.Optional;

public interface TranslationExerciseService {
    Optional<TranslationExercise> findById(Long id);
}
