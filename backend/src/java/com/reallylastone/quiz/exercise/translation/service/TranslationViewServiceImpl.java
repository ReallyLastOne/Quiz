package com.reallylastone.quiz.exercise.translation.service;

import com.reallylastone.quiz.exercise.translation.mapper.TranslationMapper;
import com.reallylastone.quiz.exercise.translation.model.Translation;
import com.reallylastone.quiz.exercise.translation.model.TranslationCreateRequest;
import com.reallylastone.quiz.exercise.translation.model.TranslationView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TranslationViewServiceImpl implements TranslationViewService {
    private final TranslationService translationService;
    private final TranslationMapper translationMapper;

    @Override
    public ResponseEntity<TranslationView> findById(Long id) {
        Optional<Translation> exerciseOptional = translationService.findById(id);
        return exerciseOptional.map(exercise -> ResponseEntity.ok(translationMapper.mapToView(exercise))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public void createTranslation(TranslationCreateRequest createRequest) {
        Translation translationExercise = translationMapper.mapToEntity(createRequest);

        translationService.createTranslation(translationExercise);
    }
}
