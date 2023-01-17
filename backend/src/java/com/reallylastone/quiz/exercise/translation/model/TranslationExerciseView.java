package com.reallylastone.quiz.exercise.translation.model;

import lombok.Data;

import java.util.Locale;
import java.util.Map;

@Data
public class TranslationExerciseView {
    private Map<Locale, String> translationMap;

    private String imagePath;
}
