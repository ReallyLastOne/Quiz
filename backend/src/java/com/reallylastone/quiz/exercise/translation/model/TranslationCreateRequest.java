package com.reallylastone.quiz.exercise.translation.model;

import java.util.Locale;
import java.util.Map;

public record TranslationCreateRequest(Map<Locale, String> translationMap) {
}
