package com.reallylastone.quiz.exercise.phrase.model;

import java.util.Locale;
import java.util.Map;

public record PhraseCreateRequest(Map<Locale, String> translationMap) {
}
