package com.reallylastone.quiz.game.core.quiz.model;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.game.session.model.GameSession;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import lombok.Data;

import java.util.Map;
import java.util.Optional;

@Data
@Entity
public class QuizGameSession extends GameSession {
    @ElementCollection
    @CollectionTable(name = "quiz_game_session_questions",
            joinColumns = @JoinColumn(name = "game_session"))
    @Column(name = "status")
    @MapKeyJoinColumn(name = "question_id", referencedColumnName = "id")
    private Map<Question, Boolean> questionsAndStatus;

    @Column(nullable = false)
    private int questionSize;

    public Question findCurrent() {
        Optional<Map.Entry<Question, Boolean>> first = questionsAndStatus.entrySet().stream().
                filter(e -> e.getValue() == null).findFirst();

        if (first.isEmpty()) throw new IllegalStateException("no question with no answer in given quiz game session");

        return first.get().getKey();
    }
}
