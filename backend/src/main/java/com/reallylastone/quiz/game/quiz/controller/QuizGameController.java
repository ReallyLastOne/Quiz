package com.reallylastone.quiz.game.quiz.controller;

import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerResponse;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import com.reallylastone.quiz.game.quiz.model.ActiveQuizGameSessionView;
import com.reallylastone.quiz.game.quiz.model.HighscoreQuizEntry;
import com.reallylastone.quiz.game.quiz.model.ListOfPlayedGamesView;
import com.reallylastone.quiz.game.quiz.service.QuizGameViewService;
import com.reallylastone.quiz.util.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuizGameController implements QuizGameOperations {
    private final QuizGameViewService quizGameViewService;

    @Override
    public ResponseEntity<GenericResponse> startGame(@RequestParam(defaultValue = "5") int questions,
            HttpServletRequest request) {
        return quizGameViewService.startGame(questions, request);
    }

    @Override
    public ResponseEntity<QuestionView> nextQuestion(HttpServletRequest request) {
        return quizGameViewService.next(request);
    }

    @Override
    public ResponseEntity<QuestionAnswerResponse> answer(@RequestBody QuestionAnswerRequest questionAnswer,
            HttpServletRequest request) {
        return quizGameViewService.answer(questionAnswer, request);
    }

    @Override
    public ResponseEntity<GenericResponse> stopGame() {
        return quizGameViewService.stopGame();
    }

    @Override
    public ResponseEntity<ActiveQuizGameSessionView> findActive() {
        return quizGameViewService.findActive();
    }

    @Override
    public ResponseEntity<ListOfPlayedGamesView> findRecent(@RequestParam(defaultValue = "5") int games) {
        return quizGameViewService.findRecent(games);
    }

    @Override
    public ResponseEntity<List<HighscoreQuizEntry>> getHighscore() {
        // TODO tests
        return quizGameViewService.getHighscore();
    }
}
