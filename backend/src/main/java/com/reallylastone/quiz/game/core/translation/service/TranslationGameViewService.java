package com.reallylastone.quiz.game.core.translation.service;

import com.reallylastone.quiz.exercise.phrase.model.PhraseToTranslate;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerResponse;
import com.reallylastone.quiz.game.core.translation.model.ActiveTranslationGameSessionView;
import com.reallylastone.quiz.util.validation.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

public interface TranslationGameViewService {
    Long startGame(Locale sourceLanguage, Locale destinationLanguage, int phrases, HttpServletRequest request);

    ResponseEntity<PhraseToTranslate> next();

    ResponseEntity<PhraseAnswerResponse> answer(PhraseAnswerRequest phraseAnswer);

    ResponseEntity<GenericResponse> stopGame();

    ResponseEntity<ActiveTranslationGameSessionView> findActive();
}
