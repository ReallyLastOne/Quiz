package com.reallylastone.quiz.exercise.phrase.service;

import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseFilter;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PhraseViewService {
    ResponseEntity<PhraseView> findById(Long id);

    ResponseEntity<PhraseView> createPhrase(PhraseCreateRequest createRequest);

    ResponseEntity<List<PhraseView>> getAllPhrases(int page, int size, PhraseFilter phraseFilter);
}
