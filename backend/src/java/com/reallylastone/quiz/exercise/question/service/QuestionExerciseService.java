package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.model.QuestionExercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionExerciseService {
    Optional<QuestionExercise> findById(Long id);

    Page<QuestionExercise> findAll(Pageable pageable);

    Optional<QuestionExercise> findRandomQuestion();
}
