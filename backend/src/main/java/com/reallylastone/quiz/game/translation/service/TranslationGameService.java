package com.reallylastone.quiz.game.translation.service;

import com.reallylastone.quiz.exercise.phrase.model.PhraseToTranslate;
import com.reallylastone.quiz.game.translation.model.HighscoreTranslationEntry;
import com.reallylastone.quiz.game.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.translation.model.TranslationGameSession;
import com.reallylastone.quiz.user.model.UserEntity;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface TranslationGameService {
    Long startGame(Locale sourceLanguage, Locale destinationLanguage, int phrases, UserEntity user);

    PhraseToTranslate next();

    boolean processAnswer(PhraseAnswerRequest phraseAnswer);

    void stopGame();

    Optional<TranslationGameSession> findActive();

    List<TranslationGameSession> findRecent(int games);

    List<HighscoreTranslationEntry> getHighsore();
}
