package com.reallylastone.quiz.exercise.translation.service;

import com.reallylastone.quiz.exercise.translation.model.TranslationExerciseResponse;
import org.springframework.http.ResponseEntity;

public interface TranslationExerciseViewService {
    ResponseEntity<TranslationExerciseResponse> findById(Long id);
}
