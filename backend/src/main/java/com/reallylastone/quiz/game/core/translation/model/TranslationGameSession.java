package com.reallylastone.quiz.game.core.translation.model;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.game.session.model.GameSession;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import lombok.Data;
import lombok.ToString;

import java.util.Locale;
import java.util.Map;

@Entity
@Data
@ToString(callSuper = true)
public class TranslationGameSession extends GameSession {
    @ElementCollection
    @CollectionTable(name = "translation_game_session_phrases",
            joinColumns = @JoinColumn(name = "game_session"))
    @Column(name = "status")
    @MapKeyJoinColumn(name = "phrase_id", referencedColumnName = "id")
    private Map<Phrase, Boolean> translationsAndStatus;

    @Column(nullable = false)
    private Locale sourceLanguage;

    @Column(nullable = false)
    private Locale destinationLanguage;
}