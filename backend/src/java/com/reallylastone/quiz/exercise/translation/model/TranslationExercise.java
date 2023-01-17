package com.reallylastone.quiz.exercise.translation.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Locale;
import java.util.Map;

@Entity
@Data
public class TranslationExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ElementCollection
    @MapKeyColumn(name = "locale")
    @Column(name = "translation")
    @CollectionTable(name = "translation_map", joinColumns = @JoinColumn(name = "translation_exercise_id"))
    private Map<Locale, String> translationMap;

    private String imagePath;
}
