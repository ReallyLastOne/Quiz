package com.reallylastone.quiz.auth.model;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(@NotBlank(message = "nickname must not be blank") String nickname,
                                    @NotBlank(message = "password must not be blank") String password) {
}