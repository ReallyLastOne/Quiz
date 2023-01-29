package com.reallylastone.quiz.exercise.translation.controller;

import com.reallylastone.quiz.exercise.translation.model.TranslationCreateRequest;
import com.reallylastone.quiz.exercise.translation.model.TranslationView;
import com.reallylastone.quiz.exercise.translation.service.TranslationViewService;
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
@RequestMapping("/api/v1/exercises/translations")
@RequiredArgsConstructor
@Tag(
        description = "Provides operations to work on Translation objects",
        name = "Translation Controller")
public class TranslationController {
    private final TranslationViewService translationViewService;

    @Operation(
            summary = "Gets Translation object by its id.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The specified Translation is present.",
                    content = @Content(schema = @Schema(implementation = TranslationView.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "The specified Translation was not found.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TranslationView> findById(@PathVariable Long id) {
        return translationViewService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<TranslationView> createTranslation(@RequestBody TranslationCreateRequest createRequest) {
        return translationViewService.createTranslation(createRequest);
    }
}
