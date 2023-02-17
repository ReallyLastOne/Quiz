package com.reallylastone.quiz.game.core.translation.service;

import java.net.http.HttpRequest;
import java.util.Locale;

public interface TranslationGameViewService {
    Long startGame(Locale sourceLanguage, Locale destinationLanguage, HttpRequest request);
}
