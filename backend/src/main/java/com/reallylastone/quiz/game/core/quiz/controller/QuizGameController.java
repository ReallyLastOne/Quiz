package com.reallylastone.quiz.game.core.quiz.controller;

import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerResponse;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import com.reallylastone.quiz.game.core.quiz.service.QuizGameViewService;
import com.reallylastone.quiz.util.validation.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuizGameController implements QuizGameOperations {
    private final QuizGameViewService quizGameViewService;

    @Override
    public ResponseEntity<GenericResponse> startGame(@RequestParam(defaultValue = "5") int questions, HttpServletRequest request) {
        return quizGameViewService.startGame(questions, request);
    }

    @Override
    public ResponseEntity<QuestionView> nextQuestion(HttpServletRequest request) {
        return quizGameViewService.next(request);
    }

    @Override
    public ResponseEntity<QuestionAnswerResponse> answer(@RequestBody QuestionAnswerRequest questionAnswer, HttpServletRequest request) {
        return quizGameViewService.answer(questionAnswer, request);
    }

    @Override
    public ResponseEntity<GenericResponse> stopGame() {
        return quizGameViewService.stopGame();
    }
}
