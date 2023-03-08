package com.reallylastone.quiz.util.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class StateValidationErrorsException extends RuntimeException {
    private final List<StateValidationError> errors;
}
