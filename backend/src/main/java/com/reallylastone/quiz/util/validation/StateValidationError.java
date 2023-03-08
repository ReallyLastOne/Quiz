package com.reallylastone.quiz.util.validation;

public enum StateValidationError {
    USER_ACTIVE_SESSION("user is already in session"), USER_INACTIVE_SESSION("user not in active requested session type"),
    USER_UNANSWERED_QUESTIONS("user has unanswered other questions");
    private final String message;

    StateValidationError(String message) {
        this.message = message;
    }
}
