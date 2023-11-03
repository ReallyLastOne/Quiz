package com.reallylastone.quiz.tag.model;

import jakarta.validation.constraints.NotBlank;

public record TagCreateRequest(@NotBlank String name, @NotBlank String description) {
}
