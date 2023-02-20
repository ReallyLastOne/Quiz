package com.reallylastone.quiz.game.core.translation.service;

import com.reallylastone.quiz.game.core.translation.model.TranslationGameSession;
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
    public Long startGame(Locale sourceLanguage, Locale destinationLanguage, UserEntity user) {
        TranslationGameSession gameSession = new TranslationGameSession();
        gameSession.setSourceLanguage(sourceLanguage);
        gameSession.setDestinationLanguage(destinationLanguage);

        return gameSessionService.createSession(gameSession, user);
    }
}
