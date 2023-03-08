package com.reallylastone.quiz.exercise.phrase.service;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;

import java.util.Optional;

public interface PhraseService {
    Optional<Phrase> findById(Long id);

    Phrase createPhrase(PhraseCreateRequest createRequest);
}
