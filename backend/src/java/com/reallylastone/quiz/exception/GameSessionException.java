package com.reallylastone.quiz.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameSessionException extends RuntimeException {
    private final LocalDateTime date;

    public GameSessionException(LocalDateTime date, String message) {
        super(message);
        this.date = date;
    }
}
