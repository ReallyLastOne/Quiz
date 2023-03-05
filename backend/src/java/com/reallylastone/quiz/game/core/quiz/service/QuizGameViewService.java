package com.reallylastone.quiz.game.core.quiz.service;

import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerResponse;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface QuizGameViewService {
    Long startGame(int questions, HttpServletRequest request);

    ResponseEntity<QuestionView> next(HttpServletRequest request);

    ResponseEntity<QuestionAnswerResponse> answer(QuestionAnswerRequest questionAnswer, HttpServletRequest request);
}
