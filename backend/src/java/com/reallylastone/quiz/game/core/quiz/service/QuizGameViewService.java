package com.reallylastone.quiz.game.core.quiz.service;

import java.net.http.HttpRequest;

public interface QuizGameViewService {
    Long startGame(int questions, HttpRequest request);
}
