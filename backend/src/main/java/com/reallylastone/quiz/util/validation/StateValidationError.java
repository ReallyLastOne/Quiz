package com.reallylastone.quiz.util.validation;

import lombok.Getter;

@Getter
public enum StateValidationError {
    USER_ACTIVE_SESSION("user.session.active"), USER_INACTIVE_SESSION("user.session.inactive"),
    USER_UNANSWERED_QUESTIONS("user.session.questions.unanswered"),
    USER_NO_QUESTION_TO_ANSWER("user.session.questions.none"),
    USER_UNANSWERED_PHRASES("user.session.phrases.unanswered"), USER_NO_PHRASE_TO_ANSWER("user.session.phrases.none");

    private final String messageKey;

    StateValidationError(String messageKey) {
        this.messageKey = messageKey;
    }
}
