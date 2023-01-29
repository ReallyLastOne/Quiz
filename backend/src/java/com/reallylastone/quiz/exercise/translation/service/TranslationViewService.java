package com.reallylastone.quiz.exercise.translation.service;

import com.reallylastone.quiz.exercise.translation.model.TranslationCreateRequest;
import com.reallylastone.quiz.exercise.translation.model.TranslationView;
import org.springframework.http.ResponseEntity;

public interface TranslationViewService {
    ResponseEntity<TranslationView> findById(Long id);

    ResponseEntity<TranslationView> createTranslation(TranslationCreateRequest createRequest);
}
