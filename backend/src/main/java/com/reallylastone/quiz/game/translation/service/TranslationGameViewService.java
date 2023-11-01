package com.reallylastone.quiz.game.translation.service;

import com.reallylastone.quiz.exercise.phrase.model.PhraseToTranslate;
import com.reallylastone.quiz.game.translation.model.ActiveTranslationGameSessionView;
import com.reallylastone.quiz.game.translation.model.ListOfPlayedGamesView;
import com.reallylastone.quiz.game.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.translation.model.PhraseAnswerResponse;
import com.reallylastone.quiz.util.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

public interface TranslationGameViewService {
    Long startGame(Locale sourceLanguage, Locale destinationLanguage, int phrases, HttpServletRequest request);

    ResponseEntity<PhraseToTranslate> next();

    ResponseEntity<PhraseAnswerResponse> answer(PhraseAnswerRequest phraseAnswer);

    ResponseEntity<GenericResponse> stopGame();

    ResponseEntity<ActiveTranslationGameSessionView> findActive();

    ResponseEntity<ListOfPlayedGamesView> findRecent(int games);
}
