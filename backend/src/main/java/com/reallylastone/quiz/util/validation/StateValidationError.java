package com.reallylastone.quiz.util.validation;

public enum StateValidationError {
    USER_ACTIVE_SESSION("user is already in session"),
    USER_INACTIVE_SESSION("user not in active requested session type"),
    USER_UNANSWERED_QUESTIONS("user has unanswered other questions"),
    USER_NO_QUESTION_TO_ANSWER("user has no question to answer"),
    USER_UNANSWERED_PHRASES("user has unanswered other phrases"),
    USER_NO_PHRASE_TO_ANSWER("user has no phrase to answer");

    private final String message;

    StateValidationError(String message) {
        this.message = message;
    }
}
