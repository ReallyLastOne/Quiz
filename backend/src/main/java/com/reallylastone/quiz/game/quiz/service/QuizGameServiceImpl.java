package com.reallylastone.quiz.game.quiz.service;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.game.quiz.model.HighscoreQuizEntry;
import com.reallylastone.quiz.game.quiz.model.QuizGameSession;
import com.reallylastone.quiz.game.session.model.GameSessionCreateRequest;
import com.reallylastone.quiz.game.session.service.GameSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<QuizGameSession> findActive() {
        return gameSessionService.findActive(QuizGameSession.class);
    }

    @Override
    public List<QuizGameSession> findRecent(int games) {
        return gameSessionService.findRecent(games, QuizGameSession.class);
    }

    @Override
    public List<HighscoreQuizEntry> getHighscore() {
        return (List<HighscoreQuizEntry>) gameSessionService.getHighscore(QuizGameSession.class);
    }
}
