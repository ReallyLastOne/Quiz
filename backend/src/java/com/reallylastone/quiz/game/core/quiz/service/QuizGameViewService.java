package com.reallylastone.quiz.game.core.quiz.service;

import jakarta.servlet.http.HttpServletRequest;

public interface QuizGameViewService {
    Long startGame(int questions, HttpServletRequest request);
}
