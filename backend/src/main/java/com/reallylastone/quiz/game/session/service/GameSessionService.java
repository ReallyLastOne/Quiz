package com.reallylastone.quiz.game.session.service;

import com.reallylastone.quiz.exercise.phrase.model.PhraseToTranslate;
import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.session.model.GameSession;
import com.reallylastone.quiz.game.session.model.GameSessionCreateRequest;

import java.util.List;
import java.util.Optional;

public interface GameSessionService {
    Long createSession(GameSessionCreateRequest request);

    Question nextQuestion();

    PhraseToTranslate nextPhrase();

    boolean processAnswer(QuestionAnswerRequest questionAnswer);

    boolean processAnswer(PhraseAnswerRequest phraseAnswer);

    void stopGame();

    <T extends GameSession> Optional<T> findActive(Class<T> gameSessionType);

    <T extends GameSession> List<T> findRecent(int games, Class<T> gameSessionType);
}
