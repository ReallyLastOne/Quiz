package com.reallylastone.quiz.game.core.quiz.service;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.game.core.quiz.model.QuizGameSession;
import com.reallylastone.quiz.game.session.model.GameSessionCreateRequest;
import com.reallylastone.quiz.game.session.service.GameSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizGameServiceImpl implements QuizGameService {
    private final GameSessionService gameSessionService;

    @Override
    public Long startGame(int questions) {
        QuizGameSession quizGameSession = new QuizGameSession();
        quizGameSession.setQuestionSize(questions);

        return gameSessionService.createSession(new GameSessionCreateRequest(quizGameSession));
    }

    @Override
    public Question next() {
        return gameSessionService.nextQuestion();
    }

    @Override
    public boolean processAnswer(QuestionAnswerRequest questionAnswer) {
        return gameSessionService.processAnswer(questionAnswer);
    }

    @Override
    public void stopGame() {
        gameSessionService.stopGame();
    }
}
