package com.reallylastone.quiz.game.quiz.service;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.game.quiz.model.HighscoreQuizEntry;
import com.reallylastone.quiz.game.quiz.model.QuizGameSession;

import java.util.List;
import java.util.Optional;

public interface QuizGameService {
    Long startGame(int questions);

    Question next();

    boolean processAnswer(QuestionAnswerRequest questionAnswer);

    void stopGame();

    Optional<QuizGameSession> findActive();

    List<QuizGameSession> findRecent(int games);

    List<HighscoreQuizEntry> getHighscore();
}
