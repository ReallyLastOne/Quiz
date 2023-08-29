package com.reallylastone.quiz.unit.exercise;

import com.reallylastone.quiz.exercise.phrase.mapper.PhraseMapper;
import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.repository.PhraseRepository;
import com.reallylastone.quiz.exercise.phrase.service.PhraseService;
import com.reallylastone.quiz.exercise.phrase.service.PhraseServiceImpl;
import com.reallylastone.quiz.exercise.phrase.validation.PhraseValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PhraseServiceTest {

    private PhraseService phraseService;
    private PhraseRepository phraseRepository;
    private PhraseValidator phraseValidator;
    private PhraseMapper phraseMapper;

    @BeforeEach
    public void setUp() {
        phraseRepository = mock(PhraseRepository.class);
        phraseValidator = mock(PhraseValidator.class);
        phraseMapper = mock(PhraseMapper.class);

        phraseService = new PhraseServiceImpl(
                phraseRepository,
                phraseValidator,
                phraseMapper
        );
    }

    @Test
    void shouldDrawRandomPhrase() {
        Phrase phrase = new Phrase();
        phrase.setTranslationMap(Map.of(Locale.ENGLISH, "whatever", Locale.GERMAN, "whateverInGerman"));
        Phrase phrase2 = new Phrase();
        phrase2.setTranslationMap(Map.of(Locale.ENGLISH, "whatever2", Locale.GERMAN, "whateverInGerman2"));

        List<Phrase> phrases = List.of(phrase, phrase2);
        when(phraseRepository.findByOwnerId(1L)).thenReturn(phrases);

        Phrase randomPhrase = phraseService.findRandomPhrase(Locale.ENGLISH, Locale.GERMAN, 1L);
        Assertions.assertTrue(phrases.contains(randomPhrase));
    }
}
