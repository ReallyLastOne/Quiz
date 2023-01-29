package com.reallylastone.quiz.exercise.phrase.service;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.repository.PhraseRepository;
import com.reallylastone.quiz.exercise.phrase.validation.PhraseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhraseServiceImpl implements PhraseService {
    private final PhraseRepository phraseRepository;
    private final PhraseValidator phraseValidator;

    @Override
    public Optional<Phrase> findById(Long id) {
        return phraseRepository.findById(id);
    }

    @Override
    @Transactional
    public Phrase createPhrase(Phrase phrase) {
        List<Phrase> phrases = phraseRepository.getByTranslationValues(phrase.getTranslationMap().values());

        phraseValidator.validate(phrase, phrases);

        if (phrases.isEmpty()) {
            phraseRepository.save(phrase);
        }

        if (phrases.size() == 1) {
            Phrase toMerge = phrases.get(0);
            toMerge.getTranslationMap().putAll(phrase.getTranslationMap());
        }

        return null;
    }
}
