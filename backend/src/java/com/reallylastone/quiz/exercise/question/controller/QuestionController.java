package com.reallylastone.quiz.exercise.question.controller;

import com.reallylastone.quiz.exercise.question.model.QuestionView;
import com.reallylastone.quiz.exercise.question.service.QuestionViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises/questions")
@RequiredArgsConstructor
@Tag(
        description = "Provides operations to work on Question objects",
        name = "Question Controller")
public class QuestionController {
    private final QuestionViewService questionViewService;

    @Operation(
            summary = "Gets Question object by its id.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The specified Question is present.",
                    content = @Content(schema = @Schema(implementation = QuestionView.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "The specified Question was not found.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<QuestionView> findById(@PathVariable Long id) {
        return questionViewService.findById(id);
    }

    @Operation(
            summary = "Gets all Question object. Supports pagination.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = QuestionView.class)))
                    })
    })
    @GetMapping
    public ResponseEntity<List<QuestionView>> findAll(@PageableDefault(size = 100) @ParameterObject Pageable page) {
        return questionViewService.findAll(page);
    }

    @Operation(
            summary = "Gets random Question.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Random Question is present.",
                    content = @Content(schema = @Schema(implementation = QuestionView.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Did not found any Question.",
                    content = @Content
            )
    })
    @GetMapping("/random")
    public ResponseEntity<QuestionView> findRandomQuestion() {
        return questionViewService.findRandomQuestion();
    }
}
