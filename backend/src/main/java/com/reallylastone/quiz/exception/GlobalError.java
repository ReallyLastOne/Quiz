package com.reallylastone.quiz.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GlobalError extends ResponseError {
    private String message;
}