package com.reallylastone.quiz.game.core.quiz.service;

import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerResponse;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import com.reallylastone.quiz.game.core.quiz.model.ActiveQuizGameSessionView;
import com.reallylastone.quiz.util.validation.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface QuizGameViewService {
    ResponseEntity<GenericResponse> startGame(int questions, HttpServletRequest request);

    ResponseEntity<QuestionView> next(HttpServletRequest request);

    ResponseEntity<QuestionAnswerResponse> answer(QuestionAnswerRequest questionAnswer, HttpServletRequest request);

    ResponseEntity<GenericResponse> stopGame();

    ResponseEntity<ActiveQuizGameSessionView> findActive();
}
