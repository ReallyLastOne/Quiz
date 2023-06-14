package com.reallylastone.quiz.exercise.phrase.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Locale;
import java.util.Map;

public record PhraseCreateRequest(@Size(min = 2, message = "translationMap must have at least 2 entries")
                                  @NotNull(message = "translationMap must not be null")
                                  Map<Locale, String> translationMap,
                                  @Nullable Boolean userPhrase) {
}
