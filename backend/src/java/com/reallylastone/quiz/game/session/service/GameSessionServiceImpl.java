package com.reallylastone.quiz.game.session.service;

import com.reallylastone.quiz.exception.GameSessionException;
import com.reallylastone.quiz.game.session.model.GameSession;
import com.reallylastone.quiz.game.session.repository.GameSessionRepository;
import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameSessionServiceImpl implements GameSessionService {
    private final GameSessionRepository gameSessionRepository;
    private final UserRepository userRepository;

    @Override
    public Long createSession(GameSession session, Long userId) {
        if (gameSessionRepository.hasActiveSession(userId))
            throw new GameSessionException(LocalDateTime.now(), "User has an active session");

        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            session.setUser(optionalUser.get());
            session.setStartDate(LocalDateTime.now());

            return gameSessionRepository.save(session).getId();
        }

        throw new GameSessionException(LocalDateTime.now(), "No User with provided ID, could not create a session");
    }
}
