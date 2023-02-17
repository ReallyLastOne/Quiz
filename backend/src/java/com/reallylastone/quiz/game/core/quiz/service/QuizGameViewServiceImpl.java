package com.reallylastone.quiz.game.core.quiz.service;

import com.reallylastone.quiz.user.model.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizGameViewServiceImpl implements QuizGameViewService {
    private final QuizGameService quizGameService;

    @Override
    public Long startGame(int questions, HttpServletRequest request) {
        return quizGameService.startGame(questions, (UserEntity)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
