package com.reallylastone.quiz.auth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(@NotBlank(message = "nickname must not be blank") String nickname,
        @NotBlank(message = "email must not be blank") @Email(message = "email must be properly formatted") String email,
        @NotBlank(message = "password must not be blank") String password) {
}
