package com.reallylastone.quiz.exercise.phrase.service;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateBatchResponse;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface PhraseService {
    Optional<Phrase> findById(Long id);

    Phrase createPhrase(PhraseCreateRequest createRequest);

    List<Phrase> getAllPhrases(PageRequest of, String[] languages);

    PhraseCreateBatchResponse createPhrases(List<Phrase> phrases);

    Phrase findRandomPhrase(Locale sourceLanguage, Locale destinationLanguage, Long userId);
}
