package com.reallylastone.cli.external.model;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record AuthenticationResponse(String accessToken, String refreshToken, String tokenType) {
}
