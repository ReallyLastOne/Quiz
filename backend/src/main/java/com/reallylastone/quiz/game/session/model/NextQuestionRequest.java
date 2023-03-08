package com.reallylastone.quiz.game.session.model;

import jakarta.validation.constraints.NotNull;

public record NextQuestionRequest(@NotNull(message = "userId must not be null") Long userId) {
}
