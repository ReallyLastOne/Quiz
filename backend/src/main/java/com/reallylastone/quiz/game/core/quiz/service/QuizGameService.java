package com.reallylastone.quiz.game.core.quiz.service;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;

public interface QuizGameService {
    Long startGame(int questions);

    Question next();

    boolean processAnswer(QuestionAnswerRequest questionAnswer);

    void stopGame();
}
