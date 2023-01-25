package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.model.QuestionExercise;

import java.util.List;
import java.util.Optional;

public interface QuestionExerciseService {
    Optional<QuestionExercise> findById(Long id);

    List<QuestionExercise> findAll(Integer page, Integer pageSize);
}
