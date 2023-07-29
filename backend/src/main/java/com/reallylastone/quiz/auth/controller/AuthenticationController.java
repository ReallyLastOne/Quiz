package com.reallylastone.quiz.auth.controller;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.AuthenticationResponse;
import com.reallylastone.quiz.auth.model.RefreshTokenResponse;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import com.reallylastone.quiz.auth.service.AuthenticationViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationOperations {
    private final AuthenticationViewService authenticationViewService;

    @Override
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return authenticationViewService.register(request);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return authenticationViewService.authenticate(request);
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refresh(HttpServletRequest request) {
        return authenticationViewService.refresh(request);
    }

    @Override
    public ResponseEntity<Void> csrf() {
        return ResponseEntity.ok().build();
    }
}
