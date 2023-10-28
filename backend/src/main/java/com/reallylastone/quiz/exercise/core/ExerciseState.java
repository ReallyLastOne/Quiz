package com.reallylastone.quiz.exercise.core;

import java.util.Objects;
import java.util.stream.Stream;

public enum ExerciseState {
    NO_ANSWER(null), CORRECT(true), WRONG(false);

    private final Boolean value;

    ExerciseState(Boolean value) {
        this.value = value;
    }

    public static ExerciseState from(Boolean isCorrectAnswer) {
        return Stream.of(ExerciseState.values()).filter(p -> Objects.equals(p.value, isCorrectAnswer)).findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
