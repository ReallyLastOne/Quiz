package com.reallylastone.quiz.game.core.quiz.model;

import java.time.LocalDateTime;

public record DoneQuizSessionView(int correctAnswers, LocalDateTime finishDate, int totalQuestions) {
}
