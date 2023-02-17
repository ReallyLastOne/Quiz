package com.reallylastone.quiz.game.core.quiz.model;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.game.session.model.GameSession;
import jakarta.persistence.*;
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

    private int questionSize;
}
