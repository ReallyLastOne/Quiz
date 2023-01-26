package com.reallylastone.quiz.exercise.translation.controller;

import com.reallylastone.quiz.exercise.translation.model.TranslationExerciseResponse;
import com.reallylastone.quiz.exercise.translation.service.TranslationExerciseViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exercise/translation")
@RequiredArgsConstructor
@Tag(
        description = "Provides operations to work on Translation Exercise objects",
        name = "Translation Exercise Controller")
public class TranslationExerciseController {
    private final TranslationExerciseViewService translationViewService;

    @Operation(
            summary = "Gets Translation object by its id.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The specified Translation is present.",
                    content = @Content(schema = @Schema(implementation = TranslationExerciseResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "The specified Translation was not found.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TranslationExerciseResponse> findById(@PathVariable Long id) {
        return translationViewService.findById(id);
    }
}
