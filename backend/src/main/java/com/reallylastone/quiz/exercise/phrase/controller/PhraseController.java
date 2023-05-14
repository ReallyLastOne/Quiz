package com.reallylastone.quiz.exercise.phrase.controller;

import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import com.reallylastone.quiz.exercise.phrase.service.PhraseViewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exercises/phrases")
@RequiredArgsConstructor
@Tag(
        description = "Provides operations to work on Phrase objects",
        name = "Phrase Controller")
public class PhraseController {
    private final PhraseViewService phraseViewService;

    @PostMapping
    public ResponseEntity<PhraseView> createPhrase(@RequestBody PhraseCreateRequest createRequest) {
        return phraseViewService.createPhrase(createRequest);
    }
}
