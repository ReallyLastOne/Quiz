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
}
