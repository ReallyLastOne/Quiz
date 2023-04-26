package com.reallylastone.quiz.auth.model;

public record AuthenticationResponse(String accessToken, String refreshToken, String tokenType) {
}
