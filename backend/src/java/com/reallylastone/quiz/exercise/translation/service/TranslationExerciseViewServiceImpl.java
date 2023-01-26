package com.reallylastone.quiz.exercise.translation.service;

import com.reallylastone.quiz.exercise.translation.mapper.TranslationExerciseMapper;
import com.reallylastone.quiz.exercise.translation.model.TranslationExercise;
import com.reallylastone.quiz.exercise.translation.model.TranslationExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TranslationExerciseViewServiceImpl implements TranslationExerciseViewService {
    private final TranslationExerciseService translationExerciseService;
    private final TranslationExerciseMapper translationExerciseMapper;

    @Override
    public ResponseEntity<TranslationExerciseResponse> findById(Long id) {
        Optional<TranslationExercise> exerciseOptional = translationExerciseService.findById(id);
        return exerciseOptional.map(exercise -> ResponseEntity.ok(translationExerciseMapper.mapToView(exercise))).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
