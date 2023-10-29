package com.reallylastone.quiz.game.core.translation.controller;

import com.reallylastone.quiz.exercise.phrase.model.PhraseToTranslate;
import com.reallylastone.quiz.game.core.translation.model.ActiveTranslationGameSessionView;
import com.reallylastone.quiz.game.core.translation.model.DoneTranslationSessionView;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerResponse;
import com.reallylastone.quiz.util.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Tag(description = "Provides operations to entirely handle a translation game", name = "Translation Game Controller")
@RequestMapping(value = "/api/v1/game/translation", produces = MediaType.APPLICATION_JSON_VALUE)
public interface TranslationGameOperations {

    @Operation(summary = "Starts a translation game for a user")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "422", description = "If user is already in different translation game or if provided parameters are wrong", content = @Content) })
    @PostMapping(value = "/start")
    Long startGame(@RequestParam Locale sourceLanguage, @RequestParam Locale destinationLanguage,
            @RequestParam(defaultValue = "5") int phrases, HttpServletRequest request);

    @Operation(summary = "Draws a random phrase for user's currently active game")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "422", description = "If user is not in quiz game or user does have already active question", content = @Content) })
    @PostMapping(value = "/next")
    ResponseEntity<PhraseToTranslate> nextPhrase(HttpServletRequest request);

    @Operation(summary = "Translates a phrase associated with active translation game session")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "422", description = "If user is not in quiz game or user does not have active question", content = @Content) })
    @PostMapping(value = "/answer", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PhraseAnswerResponse> answer(@RequestBody PhraseAnswerRequest questionAnswer,
            HttpServletRequest request);

    @Operation(summary = "Stops currently active translation game session and thus allowing to start a new one")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK") })
    @PostMapping(value = "/stop")
    ResponseEntity<GenericResponse> stopGame();

    @Operation(summary = "Fetches information about currently active translation session")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK") })
    @GetMapping(value = "/active")
    ResponseEntity<ActiveTranslationGameSessionView> findActive();

    @Operation(summary = "Fetches information about most recent translation game")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK") })
    @GetMapping(value = "/recent")
    ResponseEntity<DoneTranslationSessionView> findRecent();
}
