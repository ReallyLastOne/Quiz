package com.reallylastone.quiz.exercise.question.controller;

import java.util.List;

public record QuestionAddResponse(String content, List<String> correctAnswers, List<String> wrongAnswers, List<String> tags) {
}
