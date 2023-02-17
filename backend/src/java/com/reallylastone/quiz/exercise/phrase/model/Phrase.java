package com.reallylastone.quiz.exercise.phrase.model;

import com.reallylastone.quiz.exercise.core.Exercise;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Locale;
import java.util.Map;

@Entity
@Data
public class Phrase extends Exercise {
    @ElementCollection
    @MapKeyColumn(name = "locale")
    @Column(name = "translation")
    @CollectionTable(name = "translation_map", joinColumns = @JoinColumn(name = "phrase_id"))
    private Map<Locale, String> translationMap;

    private String imagePath;
}
