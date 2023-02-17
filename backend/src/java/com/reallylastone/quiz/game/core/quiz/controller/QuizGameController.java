package com.reallylastone.quiz.game.core.quiz.controller;

import com.reallylastone.quiz.game.core.quiz.service.QuizGameViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game/quiz")
public class QuizGameController {
    private final QuizGameViewService quizGameViewService;

    @PostMapping
    public Long startGame(@RequestParam(required = false, defaultValue = "5") int questions, HttpRequest request) {
        return quizGameViewService.startGame(questions, request);
    }
}
