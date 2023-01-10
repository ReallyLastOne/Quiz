package com.reallylastone.quiz.exercise.service;

import com.reallylastone.quiz.exercise.mapper.ExerciseMapper;
import com.reallylastone.quiz.exercise.model.Exercise;
import com.reallylastone.quiz.exercise.model.ExerciseView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseViewServiceImpl implements ExerciseViewService {
    private final ExerciseService exerciseService;
    private final ExerciseMapper exerciseMapper;

    @Override
    public ResponseEntity<ExerciseView> findById(Long id) {
        Optional<Exercise> exerciseOptional = exerciseService.findById(id);
        return exerciseOptional.map(exercise -> ResponseEntity.ok(exerciseMapper.mapToView(exercise))).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
