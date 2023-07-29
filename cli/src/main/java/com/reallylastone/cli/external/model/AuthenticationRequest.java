package com.reallylastone.cli.external.model;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record AuthenticationRequest(String nickname,
                                    String password) {
}