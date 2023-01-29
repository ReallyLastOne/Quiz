package com.reallylastone.quiz.exercise.phrase.service;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;

import java.util.Optional;

public interface PhraseService {
    Optional<Phrase> findById(Long id);

    Phrase createPhrase(Phrase phrase);
}
