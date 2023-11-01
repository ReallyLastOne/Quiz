package com.reallylastone.quiz.game.translation.model;

import java.time.LocalDateTime;

public record DoneTranslationSessionView(int correctAnswers, LocalDateTime finishDate, int totalQuestions) {
}
