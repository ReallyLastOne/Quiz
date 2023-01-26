package com.reallylastone.quiz.exercise.question.controller;

import com.reallylastone.quiz.exercise.question.model.QuestionExerciseResponse;
import com.reallylastone.quiz.exercise.question.service.QuestionExerciseViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises/questions")
@RequiredArgsConstructor
@Tag(
        description = "Provides operations to work on Question Exercise objects",
        name = "Question Exercise Controller")
public class QuestionExerciseController {
    private final QuestionExerciseViewService questionExerciseViewService;

    @Operation(
            summary = "Gets Question object by its id.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The specified Question is present.",
                    content = @Content(schema = @Schema(implementation = QuestionExerciseResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "The specified Question was not found.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<QuestionExerciseResponse> findById(@PathVariable Long id) {
        return questionExerciseViewService.findById(id);
    }

    @Operation(
            summary = "Gets all Question object. Supports pagination.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = QuestionExerciseResponse.class)))
                    })
    })
    @GetMapping
    public ResponseEntity<List<QuestionExerciseResponse>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                                  @RequestParam(defaultValue = "100") Integer pageSize) {
        return questionExerciseViewService.findAll(page, pageSize);
    }

    @Operation(
            summary = "Gets random Question.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Random Question is present.",
                    content = @Content(schema = @Schema(implementation = QuestionExerciseResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Did not found any Question.",
                    content = @Content
            )
    })
    @GetMapping("/random")
    public ResponseEntity<QuestionExerciseResponse> findRandomQuestion() {
        return questionExerciseViewService.findRandomQuestion();
    }
}
