package com.reallylastone.quiz.game.core.translation.service;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.core.translation.model.TranslationGameSession;
import com.reallylastone.quiz.game.session.model.GameSessionCreateRequest;
import com.reallylastone.quiz.game.session.service.GameSessionService;
import com.reallylastone.quiz.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TranslationGameServiceImpl implements TranslationGameService {
    private final GameSessionService gameSessionService;

    @Override
    public Long startGame(Locale sourceLanguage, Locale destinationLanguage, int phrases, UserEntity user) {
        TranslationGameSession gameSession = new TranslationGameSession();
        gameSession.setSourceLanguage(sourceLanguage);
        gameSession.setDestinationLanguage(destinationLanguage);
        gameSession.setPhrasesSize(phrases);

        return gameSessionService.createSession(new GameSessionCreateRequest(gameSession));
    }

    @Override
    public Phrase next() {
        return gameSessionService.nextPhrase();
    }

    @Override
    public boolean processAnswer(PhraseAnswerRequest phraseAnswer) {
        return gameSessionService.processAnswer(phraseAnswer);
    }

    @Override
    public void stopGame() {
        gameSessionService.stopGame();
    }
}
