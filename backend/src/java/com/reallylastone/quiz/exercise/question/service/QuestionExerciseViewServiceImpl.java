package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.mapper.QuestionExerciseMapper;
import com.reallylastone.quiz.exercise.question.model.QuestionExercise;
import com.reallylastone.quiz.exercise.question.model.QuestionExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionExerciseViewServiceImpl implements QuestionExerciseViewService {
    private final QuestionExerciseService questionExerciseService;
    private final QuestionExerciseMapper questionExerciseMapper;

    @Override
    public ResponseEntity<QuestionExerciseResponse> findById(Long id) {
        Optional<QuestionExercise> exerciseOptional = questionExerciseService.findById(id);

        return exerciseOptional.map(exercise -> ResponseEntity.ok(questionExerciseMapper.mapToView(exercise))).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<QuestionExerciseResponse>> findAll(Integer page, Integer pageSize) {
        List<QuestionExercise> all = questionExerciseService.findAll(page, pageSize);

        return ResponseEntity.ok(all.stream().map(questionExerciseMapper::mapToView).toList());
    }

    @Override
    public ResponseEntity<QuestionExerciseResponse> findRandomQuestion() {
        Optional<QuestionExercise> exerciseOptional = questionExerciseService.findRandomQuestion();

        return exerciseOptional.map(exercise -> ResponseEntity.ok(questionExerciseMapper.mapToView(exercise))).
                orElseGet(() -> ResponseEntity.notFound().build());
    }
}
