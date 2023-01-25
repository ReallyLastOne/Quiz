package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.model.QuestionExerciseView;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionExerciseViewService {
    ResponseEntity<QuestionExerciseView> findById(Long id);

    ResponseEntity<List<QuestionExerciseView>> findAll(Integer page, Integer pageSize);

    ResponseEntity<QuestionExerciseView> findRandomQuestion();
}
