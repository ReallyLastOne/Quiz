package com.reallylastone.quiz.auth.service;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.AuthenticationResponse;
import com.reallylastone.quiz.auth.model.AuthenticationServiceResponse;
import com.reallylastone.quiz.auth.model.RefreshTokenResponse;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import com.reallylastone.quiz.auth.model.UserInformation;
import com.reallylastone.quiz.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

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
    public ResponseEntity<RefreshTokenResponse> refresh(HttpServletRequest request) {
        Cookie refreshToken = WebUtils.getCookie(request, "refresh_token");

        return Optional.ofNullable(refreshToken).map(Cookie::getValue).map(authenticationService::refresh)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalArgumentException("no refresh_token cookie provided"));
    }

    @Override
    public ResponseEntity<UserInformation> me() {
        return ResponseEntity.ok(new UserInformation(UserService.getCurrentUser()));
    }

    private ResponseEntity<AuthenticationResponse> buildAuthenticationResponse(AuthenticationServiceResponse response) {
        return ResponseEntity.ok().headers(buildRefreshTokenCookie(response.refreshToken().getUuid().toString(),
                response.refreshToken().getExpirationDate())).body(toAuthenticationResponse(response));
    }

    private AuthenticationResponse toAuthenticationResponse(AuthenticationServiceResponse response) {
        return new AuthenticationResponse(response.jwtToken(), response.refreshToken().getUuid().toString(),
                response.tokenType());
    }

    private HttpHeaders buildRefreshTokenCookie(String refreshToken, LocalDateTime expirationDate) {
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken).httpOnly(true).secure(true).path("/")
                .maxAge(ChronoUnit.SECONDS.between(LocalDateTime.now(), expirationDate)).build(); // may not be precise
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.SET_COOKIE, cookie.toString());

        return headers;
    }
}
