package com.reallylastone.quiz.exercise.phrase.model;

import java.util.Locale;
import java.util.Map;

public record PhraseView(Long id, Map<Locale, String> translationMap) {
}
