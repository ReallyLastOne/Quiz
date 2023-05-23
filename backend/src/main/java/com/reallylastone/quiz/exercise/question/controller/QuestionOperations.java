package com.reallylastone.quiz.exercise.question.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(
        description = "Provides operations to work on Question objects",
        name = "Question Operations")
@RequestMapping(value = "/api/v1/exercises/questions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface QuestionOperations {
}
