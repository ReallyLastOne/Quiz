package com.reallylastone.quiz.game.core.translation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Tag(description = "Provides operations to entirely handle a translation game", name = "Translation Game Controller")
@RequestMapping(value = "/api/v1/game/translation", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface TranslationGameOperations {

    @Operation(summary = "Starts a translation game for a user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "422", description = "If user is already in different translation game or if provided parameters are wrong", content = @Content)})
    @PostMapping
    Long startGame(@RequestParam Locale sourceLanguage, @RequestParam Locale destinationLanguage, HttpServletRequest request);
}
