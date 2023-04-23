package com.reallylastone.quiz.game.core.quiz.controller;

import com.reallylastone.quiz.exercise.question.model.QuestionView;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerResponse;
import com.reallylastone.quiz.game.core.quiz.service.QuizGameViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game/quiz")
public class QuizGameController {
    private final QuizGameViewService quizGameViewService;

    @PostMapping(value = "/start", params = "questions")
    public Long startGame(@RequestParam(defaultValue = "5") int questions, HttpServletRequest request) {
        return quizGameViewService.startGame(questions, request);
    }

    @PostMapping("/next")
    public ResponseEntity<QuestionView> nextQuestion(HttpServletRequest request) {
        return quizGameViewService.next(request);
    }

    @PostMapping("/answer")
    public ResponseEntity<QuestionAnswerResponse> answer(@RequestBody QuestionAnswerRequest questionAnswer, HttpServletRequest request) {
        return quizGameViewService.answer(questionAnswer, request);
    }
}
