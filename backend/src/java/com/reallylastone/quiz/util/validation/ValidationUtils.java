package com.reallylastone.quiz.util.validation;

import jakarta.validation.ConstraintViolation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ValidationUtils {

    private ValidationUtils() {
        throw new AssertionError("The ValidationUtils methods should be accessed statically");
    }

    public static Set<? extends ConstraintViolation<?>> createConstraintViolationSet(final String fieldName, final String errorMessage) {
        return new HashSet<>(
                Collections.singletonList(
                        new SinglePropertyConstraintViolation<>(fieldName, errorMessage)));
    }
}
