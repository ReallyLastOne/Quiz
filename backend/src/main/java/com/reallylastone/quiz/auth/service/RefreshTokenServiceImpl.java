package com.reallylastone.quiz.auth.service;

import com.reallylastone.quiz.auth.repository.RefreshTokenRepository;
import com.reallylastone.quiz.auth.model.RefreshToken;
import com.reallylastone.quiz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${auth.jwt.token.refresh.expiration}")
    private Long refreshTokenExpiration;

    @Override
    public String createToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId).orElseThrow());
        refreshToken.setExpirationDate(LocalDateTime.now().plus(refreshTokenExpiration, ChronoUnit.MILLIS));
        refreshToken.setUuid(UUID.randomUUID());

        refreshTokenRepository.save(refreshToken);

        return refreshToken.getUuid().toString();
    }

    @Override
    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByUuid(UUID.fromString(refreshToken));
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpirationDate().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new CredentialsExpiredException("refresh token has expired");
        }

        return token;
    }
}