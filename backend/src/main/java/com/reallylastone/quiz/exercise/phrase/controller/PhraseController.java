package com.reallylastone.quiz.exercise.phrase.controller;

import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import com.reallylastone.quiz.exercise.phrase.service.PhraseViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PhraseController implements PhraseOperations {
    private final PhraseViewService phraseViewService;

    @Override
    public ResponseEntity<PhraseView> createPhrase(@RequestBody PhraseCreateRequest createRequest) {
        return phraseViewService.createPhrase(createRequest);
    }
}
