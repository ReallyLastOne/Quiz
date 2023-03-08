package com.reallylastone.quiz.game.core.translation.controller;

import com.reallylastone.quiz.game.core.translation.service.TranslationGameViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game/translation")
public class TranslationGameController {
    private final TranslationGameViewService translationGameViewService;

    @PostMapping
    public Long startGame(@RequestParam Locale sourceLanguage, @RequestParam Locale destinationLanguage, HttpServletRequest request) {
        return translationGameViewService.startGame(sourceLanguage, destinationLanguage, request);
    }
}
