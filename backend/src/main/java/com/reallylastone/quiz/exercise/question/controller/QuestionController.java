package com.reallylastone.quiz.exercise.question.controller;

import com.reallylastone.quiz.exercise.question.service.QuestionViewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exercises/questions")
@RequiredArgsConstructor
@Tag(
        description = "Provides operations to work on Question objects",
        name = "Question Controller")
public class QuestionController {
    private final QuestionViewService questionViewService;
}
