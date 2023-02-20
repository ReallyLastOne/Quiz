package com.reallylastone.quiz.game.session.service;

import com.reallylastone.quiz.game.session.model.GameSession;
import com.reallylastone.quiz.user.model.UserEntity;

public interface GameSessionService {
    Long createSession(GameSession session, UserEntity userEntity);
}
