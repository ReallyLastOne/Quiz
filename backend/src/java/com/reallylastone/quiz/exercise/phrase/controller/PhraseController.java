package com.reallylastone.quiz.exercise.phrase.controller;

import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import com.reallylastone.quiz.exercise.phrase.service.PhraseViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exercises/phrases")
@RequiredArgsConstructor
@Tag(
        description = "Provides operations to work on Phrase objects",
        name = "Phrase Controller")
public class PhraseController {
    private final PhraseViewService phraseViewService;

    @Operation(
            summary = "Gets Phrase object by its id.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The specified Phrase is present.",
                    content = @Content(schema = @Schema(implementation = PhraseView.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "The specified Phrase was not found.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PhraseView> findById(@PathVariable Long id) {
        return phraseViewService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<PhraseView> createPhrase(@RequestBody PhraseCreateRequest createRequest) {
        return phraseViewService.createPhrase(createRequest);
    }
}
