package com.reallylastone.quiz.exercise.service;

import com.reallylastone.quiz.exercise.model.Exercise;

import java.util.Optional;

public interface ExerciseService {
    Optional<Exercise> findById(Long id);
}
