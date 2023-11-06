package com.reallylastone.quiz.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FieldError extends ResponseError {
    private String field;
    private String message;
}
