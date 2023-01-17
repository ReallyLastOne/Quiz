package com.reallylastone.quiz.exercise.translation.service;

import com.reallylastone.quiz.exercise.translation.model.TranslationExerciseView;
import org.springframework.http.ResponseEntity;

public interface TranslationExerciseViewService {
    ResponseEntity<TranslationExerciseView> findById(Long id);
}
