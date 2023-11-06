package com.reallylastone.quiz.game.quiz.model;

import java.time.LocalDateTime;

public record DoneQuizSessionView(int correctAnswers, LocalDateTime finishDate, int totalQuestions) {
}
