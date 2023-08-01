package com.reallylastone.quiz.exercise.phrase.model;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public record PhraseFilter(List<String> locales) implements Predicate<Phrase> {
    @Override
    public boolean test(Phrase phrase) {
        return new HashSet<>(phrase.getTranslationMap().keySet().stream().map(Locale::toLanguageTag).toList()).containsAll(locales);
    }
}

