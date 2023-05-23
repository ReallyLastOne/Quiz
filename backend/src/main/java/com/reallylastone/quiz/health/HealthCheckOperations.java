package com.reallylastone.quiz.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(description = "Provides operations to check liveness of application", name = "Health Check Controller")
@RequestMapping(value = "/api/v1/health-check", produces = MediaType.TEXT_PLAIN_VALUE)
public interface HealthCheckOperations {
    @Operation(summary = "Returns health check of the application")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    @GetMapping
    String healthCheck();
}
