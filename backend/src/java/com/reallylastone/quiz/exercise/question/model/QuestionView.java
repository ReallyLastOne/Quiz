package com.reallylastone.quiz.exercise.question.model;

import java.util.List;

public record QuestionView(String content, String correctAnswer, List<String> wrongAnswers) {
}
