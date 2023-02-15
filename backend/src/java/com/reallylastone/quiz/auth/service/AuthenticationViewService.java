package com.reallylastone.quiz.auth.service;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.AuthenticationResponse;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationViewService {
    ResponseEntity<AuthenticationResponse> register(RegisterRequest request);

    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);
}
