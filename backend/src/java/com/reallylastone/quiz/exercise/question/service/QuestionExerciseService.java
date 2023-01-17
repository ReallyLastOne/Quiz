package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.model.QuestionExercise;

import java.util.Optional;

public interface QuestionExerciseService {
    Optional<QuestionExercise> findById(Long id);
}
