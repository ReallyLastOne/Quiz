package com.reallylastone.quiz.game.core.translation.model;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.game.session.model.GameSession;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Locale;
import java.util.Map;

@Entity
@Data
public class TranslationGameSession extends GameSession {
    @ElementCollection
    @CollectionTable(name = "translation_game_session_phrases",
            joinColumns = @JoinColumn(name = "game_session"))
    @Column(name = "status")
    @MapKeyJoinColumn(name = "phrase_id", referencedColumnName = "id")
    private Map<Phrase, Boolean> translationsAndStatus;

    private Locale sourceLanguage;
    private Locale destinationLanguage;
}
