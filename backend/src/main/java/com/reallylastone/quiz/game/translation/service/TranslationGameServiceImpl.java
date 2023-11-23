package com.reallylastone.quiz.game.translation.service;

import com.reallylastone.quiz.exercise.phrase.model.PhraseToTranslate;
import com.reallylastone.quiz.game.session.model.GameSessionCreateRequest;
import com.reallylastone.quiz.game.session.service.GameSessionService;
import com.reallylastone.quiz.game.translation.model.HighscoreTranslationEntry;
import com.reallylastone.quiz.game.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.translation.model.TranslationGameSession;
import com.reallylastone.quiz.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
    public PhraseToTranslate next() {
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

    @Override
    public Optional<TranslationGameSession> findActive() {
        return gameSessionService.findActive(TranslationGameSession.class);
    }

    @Override
    public List<TranslationGameSession> findRecent(int games) {
        return gameSessionService.findRecent(games, TranslationGameSession.class);
    }

    @Override
    public List<HighscoreTranslationEntry> getHighsore() {
        return (List<HighscoreTranslationEntry>) gameSessionService.getHighscore(TranslationGameSession.class);
    }
}
