package com.reallylastone.quiz.exercise.question.model;

public record QuestionAnswerResponse(boolean correctAnswer, int questionsLeft, long totalCorrect) {
}
