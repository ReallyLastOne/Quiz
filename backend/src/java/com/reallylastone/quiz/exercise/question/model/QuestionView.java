package com.reallylastone.quiz.exercise.question.model;

import java.util.List;

public record QuestionView(Long id, String content, List<String> answers) {
}
