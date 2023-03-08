package com.reallylastone.quiz.util.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.Errors;

@AllArgsConstructor
@Getter
public class ValidationErrorsException extends RuntimeException {
    private final Errors errors;
}
