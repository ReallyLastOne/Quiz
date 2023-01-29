package com.reallylastone.quiz.exercise.question.model;

import java.util.List;

public record QuestionExerciseResponse(String question, String correctAnswer, List<String> wrongAnswers) {
}
