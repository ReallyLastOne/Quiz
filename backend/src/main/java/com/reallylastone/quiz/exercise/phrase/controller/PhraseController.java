package com.reallylastone.quiz.exercise.phrase.controller;

import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseFilter;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import com.reallylastone.quiz.exercise.phrase.service.PhraseViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PhraseController implements PhraseOperations {
    private final PhraseViewService phraseViewService;

    @Override
    public ResponseEntity<PhraseView> createPhrase(@RequestBody PhraseCreateRequest createRequest) {
        return phraseViewService.createPhrase(createRequest);
    }

    @Override
    public ResponseEntity<List<PhraseView>> getAllPhrases(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "100") int size,
                                                          @RequestBody(required = false) PhraseFilter phraseFilter) {
        return phraseViewService.getAllPhrases(page, size, phraseFilter);
    }
}
