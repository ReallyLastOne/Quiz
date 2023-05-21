package com.reallylastone.quiz.health;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController implements HealthCheckOperations {
    @Override
    public String healthCheck() {
        return "OK";
    }
}
