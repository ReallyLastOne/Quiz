package com.reallylastone.quiz.auth.model;

public record AuthenticationServiceResponse(String jwtToken, RefreshToken refreshToken, String tokenType) {
}
