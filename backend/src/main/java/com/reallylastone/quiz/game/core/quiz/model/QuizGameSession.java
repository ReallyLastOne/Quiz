package com.reallylastone.quiz.game.core.quiz.model;

import com.reallylastone.quiz.exercise.core.ExerciseState;
import com.reallylastone.quiz.exercise.question.model.Question;
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
import java.util.Map;
import java.util.Optional;

import static com.reallylastone.quiz.exercise.core.ExerciseState.NO_ANSWER;

@Data
@ToString(callSuper = true)
@Entity
public class QuizGameSession extends GameSession {
    @ElementCollection
    @CollectionTable(name = "quiz_game_session_questions",
            joinColumns = @JoinColumn(name = "game_session"))
    @Column(name = "status")
    @MapKeyJoinColumn(name = "question_id", referencedColumnName = "id")
    private Map<Question, ExerciseState> questionsAndStatus = new HashMap<>();

    @Column(nullable = false)
    private int questionSize;

    public void answer(Question question, ExerciseState answer) {
        questionsAndStatus.put(question, answer);
    }

    public Optional<Map.Entry<Question, ExerciseState>> findCurrent() {
        return questionsAndStatus.entrySet().stream().
                filter(e -> NO_ANSWER.equals(e.getValue())).findFirst();
    }

    public boolean isLastQuestion() {
        return questionSize == questionsAndStatus.size();
    }

    public long countOf(ExerciseState state) {
        return questionsAndStatus.values().stream().filter(state::equals).count();
    }

    public int leftQuestions() {
        return questionSize - questionsAndStatus.size();
    }
}