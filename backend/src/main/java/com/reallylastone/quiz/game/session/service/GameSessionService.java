package com.reallylastone.quiz.game.session.service;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.game.session.model.GameSessionCreateRequest;
import com.reallylastone.quiz.game.session.model.NextPhraseRequest;

public interface GameSessionService {
    Long createSession(GameSessionCreateRequest request);

    Question nextQuestion();

    Phrase nextPhrase(NextPhraseRequest request);

    boolean processAnswer(QuestionAnswerRequest questionAnswer);

    void stopGame();
}
