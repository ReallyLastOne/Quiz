package com.reallylastone.quiz.exercise.phrase.model;

import java.util.Locale;

public record PhraseToTranslate(String phrase, Locale sourceLanguage, Locale destinationLanguage) {
}
