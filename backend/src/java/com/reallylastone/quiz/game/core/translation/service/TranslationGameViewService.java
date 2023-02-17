package com.reallylastone.quiz.game.core.translation.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

public interface TranslationGameViewService {
    Long startGame(Locale sourceLanguage, Locale destinationLanguage, HttpServletRequest request);
}
