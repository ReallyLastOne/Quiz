package com.reallylastone.quiz.game.core.translation.model;

import com.reallylastone.quiz.exercise.core.ExerciseState;
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static com.reallylastone.quiz.exercise.core.ExerciseState.NO_ANSWER;

@Entity
@Data
@ToString(callSuper = true)
public class TranslationGameSession extends GameSession {
    @ElementCollection
    @CollectionTable(name = "translation_game_session_phrases",
            joinColumns = @JoinColumn(name = "game_session"))
    @Column(name = "status")
    @MapKeyJoinColumn(name = "phrase_id", referencedColumnName = "id")
    private Map<Phrase, ExerciseState> translationsAndStatus = new HashMap<>();

    @Column(nullable = false)
    private Locale sourceLanguage;

    @Column(nullable = false)
    private Locale destinationLanguage;

    @Column(nullable = false)
    private int phrasesSize;

    public void answer(Phrase phrase, ExerciseState answer) {
        translationsAndStatus.put(phrase, answer);
    }

    public Optional<Map.Entry<Phrase, ExerciseState>> findCurrent() {
        return translationsAndStatus.entrySet().stream().
                filter(e -> NO_ANSWER.equals(e.getValue())).findFirst();
    }

    public boolean isLastPhrase() {
        return phrasesSize == translationsAndStatus.size();
    }

    public long countOf(ExerciseState state) {
        return translationsAndStatus.values().stream().filter(state::equals).count();
    }
}
