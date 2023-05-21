package com.reallylastone.quiz.game.core.translation.controller;

import com.reallylastone.quiz.game.core.translation.service.TranslationGameViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class TranslationGameController implements TranslationGameOperations {
    private final TranslationGameViewService translationGameViewService;

    @Override
    public Long startGame(@RequestParam Locale sourceLanguage, @RequestParam Locale destinationLanguage, HttpServletRequest request) {
        return translationGameViewService.startGame(sourceLanguage, destinationLanguage, request);
    }
}
