package com.reallylastone.quiz.game.translation.controller;

import com.reallylastone.quiz.exercise.phrase.model.PhraseToTranslate;
import com.reallylastone.quiz.game.translation.model.ActiveTranslationGameSessionView;
import com.reallylastone.quiz.game.translation.model.HighscoreTranslationEntry;
import com.reallylastone.quiz.game.translation.model.ListOfPlayedGamesView;
import com.reallylastone.quiz.game.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.translation.model.PhraseAnswerResponse;
import com.reallylastone.quiz.game.translation.service.TranslationGameViewService;
import com.reallylastone.quiz.util.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class TranslationGameController implements TranslationGameOperations {
    private final TranslationGameViewService translationGameViewService;

    @Override
    public Long startGame(@RequestParam Locale sourceLanguage, @RequestParam Locale destinationLanguage,
            @RequestParam(defaultValue = "5") int phrases, HttpServletRequest request) {
        return translationGameViewService.startGame(sourceLanguage, destinationLanguage, phrases, request);
    }

    @Override
    public ResponseEntity<PhraseToTranslate> nextPhrase(HttpServletRequest request) {
        return translationGameViewService.next();
    }

    @Override
    public ResponseEntity<PhraseAnswerResponse> answer(PhraseAnswerRequest phraseAnswer, HttpServletRequest request) {
        return translationGameViewService.answer(phraseAnswer);
    }

    @Override
    public ResponseEntity<GenericResponse> stopGame() {
        return translationGameViewService.stopGame();
    }

    @Override
    public ResponseEntity<ActiveTranslationGameSessionView> findActive() {
        return translationGameViewService.findActive();
    }

    @Override
    public ResponseEntity<ListOfPlayedGamesView> findRecent(@RequestParam(defaultValue = "5") int games) {
        return translationGameViewService.findRecent(games);
    }

    @Override
    public ResponseEntity<List<HighscoreTranslationEntry>> getHighscore() {
        // TODO tests
        return translationGameViewService.getHighscore();
    }
}
