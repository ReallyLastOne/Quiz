package com.reallylastone.quiz.exercise.question.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record QuestionCreateRequest(
        @NotNull @Size(min = 1, message = "question must have at least 1 correct answer") List<String> correctAnswers,
        @NotNull @Size(min = 1, message = "question must have at least 1 wrong answer") List<String> wrongAnswers,
        @NotBlank(message = "question must have content") String content, List<String> tags) {
}