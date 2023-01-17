package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.mapper.QuestionExerciseMapper;
import com.reallylastone.quiz.exercise.question.model.QuestionExercise;
import com.reallylastone.quiz.exercise.question.model.QuestionExerciseView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionExerciseViewServiceImpl implements QuestionExerciseViewService {
    private final QuestionExerciseService questionExerciseService;
    private final QuestionExerciseMapper questionExerciseMapper;

    @Override
    public ResponseEntity<QuestionExerciseView> findById(Long id) {
        Optional<QuestionExercise> exerciseOptional = questionExerciseService.findById(id);
        return exerciseOptional.map(exercise -> ResponseEntity.ok(questionExerciseMapper.mapToView(exercise))).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
