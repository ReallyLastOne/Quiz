package com.reallylastone.quiz.game.session.service;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.session.model.GameSessionCreateRequest;

public interface GameSessionService {
    Long createSession(GameSessionCreateRequest request);

    Question nextQuestion();

    Phrase nextPhrase();

    boolean processAnswer(QuestionAnswerRequest questionAnswer);

    boolean processAnswer(PhraseAnswerRequest phraseAnswer);

    void stopGame();
}
