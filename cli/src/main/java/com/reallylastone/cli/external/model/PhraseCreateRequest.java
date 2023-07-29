package com.reallylastone.cli.external.model;

import io.micronaut.core.annotation.Introspected;

import java.util.Locale;
import java.util.Map;

@Introspected
public record PhraseCreateRequest(
        Map<Locale, String> translationMap,
        Boolean userPhrase) {
}
