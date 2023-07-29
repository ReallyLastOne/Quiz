package com.reallylastone.cli.external.model;

import io.micronaut.core.annotation.Introspected;

import java.util.Locale;
import java.util.Map;

@Introspected
public record PhraseView(Long id, Map<Locale, String> translationMap) {
}
