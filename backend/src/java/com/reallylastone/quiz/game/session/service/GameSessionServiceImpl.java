package com.reallylastone.quiz.game.session.service;

import com.reallylastone.quiz.exception.GameSessionException;
import com.reallylastone.quiz.game.session.model.GameSession;
import com.reallylastone.quiz.game.session.repository.GameSessionRepository;
import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GameSessionServiceImpl implements GameSessionService {
    private final GameSessionRepository gameSessionRepository;
    private final UserRepository userRepository;

    @Override
    public Long createSession(GameSession session, UserEntity user) {
        boolean hasActiveSession = false;
        if (hasActiveSession) throw new GameSessionException(LocalDateTime.now(), "User has an active session");

        session.setStartDate(LocalDateTime.now());
        return gameSessionRepository.save(session).getId();
    }
}
