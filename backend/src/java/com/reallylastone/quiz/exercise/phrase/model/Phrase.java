package com.reallylastone.quiz.exercise.phrase.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Locale;
import java.util.Map;

@Entity
@Data
public class Phrase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ElementCollection
    @MapKeyColumn(name = "locale")
    @Column(name = "translation")
    @CollectionTable(name = "translation_map", joinColumns = @JoinColumn(name = "translation_id"))
    private Map<Locale, String> translationMap;

    private String imagePath;
}
