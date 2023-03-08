package com.reallylastone.quiz.auth.service;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.AuthenticationResponse;
import com.reallylastone.quiz.auth.model.RefreshTokenRequest;
import com.reallylastone.quiz.auth.model.RefreshTokenResponse;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import jakarta.validation.Valid;

public interface AuthenticationService {
    AuthenticationResponse register(@Valid RegisterRequest request);

    AuthenticationResponse authenticate(@Valid AuthenticationRequest request);

    RefreshTokenResponse refresh(RefreshTokenRequest request);
}
