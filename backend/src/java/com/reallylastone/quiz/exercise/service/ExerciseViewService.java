package com.reallylastone.quiz.exercise.service;

import com.reallylastone.quiz.exercise.model.ExerciseView;
import org.springframework.http.ResponseEntity;

public interface ExerciseViewService {
    ResponseEntity<ExerciseView> findById(Long id);
}
