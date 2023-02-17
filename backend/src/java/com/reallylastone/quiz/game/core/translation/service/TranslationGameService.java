package com.reallylastone.quiz.game.core.translation.service;

import com.reallylastone.quiz.user.model.UserEntity;

import java.util.Locale;

public interface TranslationGameService {
    Long startGame(Locale sourceLanguage, Locale destinationLanguage, UserEntity user);
}
