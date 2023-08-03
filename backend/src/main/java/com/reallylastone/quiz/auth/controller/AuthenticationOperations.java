package com.reallylastone.quiz.auth.controller;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.AuthenticationResponse;
import com.reallylastone.quiz.auth.model.RefreshTokenResponse;
import com.reallylastone.quiz.auth.model.RegisterRequest;
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

@Tag(description = "Provides operations to work with user authentication", name = "Authentication Controller")
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface AuthenticationOperations {

    @Operation(summary = "Register given user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "422", description = "If provided credentials are taken by another user or if credentials are semantically wrong", content = @Content)})
    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request);

    @Operation(summary = "Authenticates given user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "422", description = "If provided credentials are semantically wrong", content = @Content)})
    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);

    @Operation(summary = "Extends the lifespan of the access token based on given refresh token in cookie")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "422", description = "If provided credentials are semantically wrong or no refresh_token cookie provided", content = @Content)})
    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    ResponseEntity<RefreshTokenResponse> refresh(HttpServletRequest request);

    @Operation(summary = "Returns XSRF-TOKEN in cookie to be used in further requests. To pass the CSRF protection the cookie and 'X-XSRF-TOKEN' header.")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK"))
    @GetMapping(value = "/csrf", produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
    ResponseEntity<Void> csrf();
}
