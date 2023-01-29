package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.model.QuestionExerciseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionExerciseViewService {
    ResponseEntity<QuestionExerciseResponse> findById(Long id);

    ResponseEntity<List<QuestionExerciseResponse>> findAll(Pageable page);

    ResponseEntity<QuestionExerciseResponse> findRandomQuestion();
}
