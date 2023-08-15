package com.reallylastone.quiz.exercise.phrase.model;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Response of request for batch phrases creation.
 *
 * @param correct   map of correctly validated phrases where key is row number of phrase in request and value is resulting translation map of phrase
 * @param incorrect map of incorrect phrases where key is row number from of phrase in request and value is list of validation messages
 */
public record PhraseCreateBatchResponse(Map<Long, Map<Locale, String>> correct, Map<Long, List<String>> incorrect) {
}
