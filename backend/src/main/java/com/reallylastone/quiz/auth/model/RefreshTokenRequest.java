package com.reallylastone.quiz.auth.model;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(@NotBlank(message = "refresh token must not be blank") String refreshToken) {
}
