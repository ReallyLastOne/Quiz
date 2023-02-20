package com.reallylastone.quiz.game.session.service;

import com.reallylastone.quiz.game.session.model.GameSession;
import com.reallylastone.quiz.game.session.repository.GameSessionRepository;
import com.reallylastone.quiz.game.session.validation.GameSessionValidator;
import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.user.repository.UserRepository;
import com.reallylastone.quiz.util.validation.ValidationErrorsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GameSessionServiceImpl implements GameSessionService {
    private final GameSessionRepository gameSessionRepository;
    private final UserRepository userRepository;
    private final GameSessionValidator gameSessionValidator;

    @Override
    public Long createSession(GameSession session, UserEntity user) {
        session.setUser(user);

        session.setStartDate(LocalDateTime.now());
        validate(session);

        return gameSessionRepository.save(session).getId();
    }

    private void validate(GameSession session) {
        Errors errors = new BeanPropertyBindingResult(session, "GameSession");
        gameSessionValidator.validate(session, errors);

        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
    }
}
