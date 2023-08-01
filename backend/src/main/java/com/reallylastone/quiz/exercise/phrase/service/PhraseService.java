package com.reallylastone.quiz.exercise.phrase.service;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseFilter;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface PhraseService {
    Optional<Phrase> findById(Long id);

    Phrase createPhrase(PhraseCreateRequest createRequest);

    List<Phrase> getAllPhrases(PageRequest of, PhraseFilter phraseFilter);
}
