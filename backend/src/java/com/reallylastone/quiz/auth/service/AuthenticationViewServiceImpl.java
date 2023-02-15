package com.reallylastone.quiz.auth.service;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.AuthenticationResponse;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationViewServiceImpl implements AuthenticationViewService {
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
