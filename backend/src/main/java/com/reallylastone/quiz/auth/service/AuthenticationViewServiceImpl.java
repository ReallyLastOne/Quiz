package com.reallylastone.quiz.auth.service;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.AuthenticationResponse;
import com.reallylastone.quiz.auth.model.AuthenticationServiceResponse;
import com.reallylastone.quiz.auth.model.RefreshTokenRequest;
import com.reallylastone.quiz.auth.model.RefreshTokenResponse;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthenticationViewServiceImpl implements AuthenticationViewService {
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        return buildAuthenticationResponse(authenticationService.register(request));
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        return buildAuthenticationResponse(authenticationService.authenticate(request));
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refresh(RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationService.refresh(request));
    }

    private ResponseEntity<AuthenticationResponse> buildAuthenticationResponse(AuthenticationServiceResponse response) {
        return ResponseEntity.ok().headers(buildRefreshTokenCookie(response.refreshToken().getUuid().toString(), response.refreshToken().getExpirationDate())).body(toAuthenticationResponse(response));
    }

    private AuthenticationResponse toAuthenticationResponse(AuthenticationServiceResponse response) {
        return new AuthenticationResponse(response.jwtToken(), response.refreshToken().getUuid().toString(), response.tokenType());
    }

    private HttpHeaders buildRefreshTokenCookie(String refreshToken, LocalDateTime expirationDate) {
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(ChronoUnit.SECONDS.between(LocalDateTime.now(), expirationDate)).build(); // may not be precise
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.SET_COOKIE, cookie.toString());

        return headers;
    }
}
