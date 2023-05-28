package com.reallylastone.quiz.game.core.quiz.controller;

import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerResponse;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(description = "Provides operations to entirely handle a quiz game. Operations are executed for a user associated with given authentication", name = "Quiz Game Controller")
@RequestMapping(value = "/api/v1/game/quiz")
public interface QuizGameOperations {

    @Operation(summary = "Starts a quiz game for a user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "422", description = "If user is already in different quiz game or if provided questions parameter is wrong", content = @Content)})
    @PostMapping(value = "/start", params = "questions", produces = MediaType.APPLICATION_JSON_VALUE)
    Long startGame(@RequestParam(defaultValue = "5") int questions, HttpServletRequest request);

    @Operation(summary = "Draws a random question for user's currently active game")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "422", description = "If user is not in quiz game or user does have already active question", content = @Content)})
    @PostMapping(value = "/next", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<QuestionView> nextQuestion(HttpServletRequest request);

    @Operation(summary = "Answers a question associated with active quiz game session")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "422", description = "If user is not in quiz game or user does not have active question", content = @Content)})
    @PostMapping(value = "/answer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<QuestionAnswerResponse> answer(@RequestBody QuestionAnswerRequest questionAnswer, HttpServletRequest request);
}