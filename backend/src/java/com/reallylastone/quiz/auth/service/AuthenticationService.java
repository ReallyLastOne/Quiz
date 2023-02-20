package com.reallylastone.quiz.auth.service;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.AuthenticationResponse;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import jakarta.validation.Valid;

public interface AuthenticationService {
    AuthenticationResponse register(@Valid RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
