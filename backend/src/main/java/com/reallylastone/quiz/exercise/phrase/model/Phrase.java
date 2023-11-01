package com.reallylastone.quiz.exercise.phrase.model;

import com.reallylastone.quiz.exercise.Exercise;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import lombok.Data;

import java.time.LocalDateTime;
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

    private Long ownerId;

    private LocalDateTime addDate;

    public boolean isCorrect(String translation, Locale destinationLanguage) {
        return translationMap.get(destinationLanguage).equals(translation);
    }
}
