package com.reallylastone.quiz.auth.service;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.AuthenticationServiceResponse;
import com.reallylastone.quiz.auth.model.RefreshTokenResponse;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import jakarta.validation.Valid;

public interface AuthenticationService {
    AuthenticationServiceResponse register(@Valid RegisterRequest request);

    AuthenticationServiceResponse authenticate(@Valid AuthenticationRequest request);

    RefreshTokenResponse refresh(String refreshToken);
}
