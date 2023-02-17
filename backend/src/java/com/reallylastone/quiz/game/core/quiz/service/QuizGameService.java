package com.reallylastone.quiz.game.core.quiz.service;

import com.reallylastone.quiz.user.model.UserEntity;

public interface QuizGameService {
    Long startGame(int questions, UserEntity user);
}
