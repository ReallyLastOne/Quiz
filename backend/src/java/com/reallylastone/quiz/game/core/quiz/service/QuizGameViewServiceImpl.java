package com.reallylastone.quiz.game.core.quiz.service;

import com.reallylastone.quiz.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;

@Service
@RequiredArgsConstructor
public class QuizGameViewServiceImpl implements QuizGameViewService {
    private final QuizGameService quizGameService;

    @Override
    public Long startGame(int questions, HttpRequest request) {
        return quizGameService.startGame(questions, (UserEntity)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
