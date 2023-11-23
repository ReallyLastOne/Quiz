package com.reallylastone.quiz.game.quiz.service;

import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerResponse;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import com.reallylastone.quiz.game.quiz.model.ActiveQuizGameSessionView;
import com.reallylastone.quiz.game.quiz.model.HighscoreQuizEntry;
import com.reallylastone.quiz.game.quiz.model.ListOfPlayedGamesView;
import com.reallylastone.quiz.util.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuizGameViewService {
    ResponseEntity<GenericResponse> startGame(int questions, HttpServletRequest request);

    ResponseEntity<QuestionView> next(HttpServletRequest request);

    ResponseEntity<QuestionAnswerResponse> answer(QuestionAnswerRequest questionAnswer, HttpServletRequest request);

    ResponseEntity<GenericResponse> stopGame();

    ResponseEntity<ActiveQuizGameSessionView> findActive();

    ResponseEntity<ListOfPlayedGamesView> findRecent(int games);

    ResponseEntity<List<HighscoreQuizEntry>> getHighscore();
}
