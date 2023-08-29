package com.reallylastone.quiz.game.core.translation.service;

import com.reallylastone.quiz.exercise.phrase.model.PhraseToTranslate;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.user.model.UserEntity;

import java.util.Locale;

public interface TranslationGameService {
    Long startGame(Locale sourceLanguage, Locale destinationLanguage, int phrases, UserEntity user);

    PhraseToTranslate next();

    boolean processAnswer(PhraseAnswerRequest phraseAnswer);

    void stopGame();
}
