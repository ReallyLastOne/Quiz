package com.reallylastone.quiz.game.core.quiz.service;

import com.reallylastone.quiz.game.core.quiz.model.QuizGameSession;
import com.reallylastone.quiz.game.session.service.GameSessionService;
import com.reallylastone.quiz.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizGameServiceImpl implements QuizGameService {
    private final GameSessionService gameSessionService;

    @Override
    public Long startGame(int questions, UserEntity user) {
        QuizGameSession quizGameSession = new QuizGameSession();
        quizGameSession.setQuestionSize(questions);

        return gameSessionService.createSession(quizGameSession, user);
    }
}
