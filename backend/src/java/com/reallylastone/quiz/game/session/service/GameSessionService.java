package com.reallylastone.quiz.game.session.service;

import com.reallylastone.quiz.game.session.model.GameSession;

public interface GameSessionService {
    Long createSession(GameSession session, Long userId);
}
