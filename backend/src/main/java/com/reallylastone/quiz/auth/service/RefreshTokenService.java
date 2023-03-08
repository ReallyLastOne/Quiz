package com.reallylastone.quiz.auth.service;

import com.reallylastone.quiz.auth.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    String createToken(Long userId);

    Optional<RefreshToken> findByToken(String refreshToken);

    RefreshToken verifyExpiration(RefreshToken token);
}