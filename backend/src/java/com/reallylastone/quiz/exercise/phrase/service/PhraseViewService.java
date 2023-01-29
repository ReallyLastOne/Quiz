package com.reallylastone.quiz.exercise.phrase.service;

import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import org.springframework.http.ResponseEntity;

public interface PhraseViewService {
    ResponseEntity<PhraseView> findById(Long id);

    ResponseEntity<PhraseView> createPhrase(PhraseCreateRequest createRequest);
}
