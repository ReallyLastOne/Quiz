package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.model.QuestionExerciseView;
import org.springframework.http.ResponseEntity;

public interface QuestionExerciseViewService {
    ResponseEntity<QuestionExerciseView> findById(Long id);
}
