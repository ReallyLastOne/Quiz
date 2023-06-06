package com.reallylastone.quiz.exercise.question.model;

import com.reallylastone.quiz.exercise.core.Exercise;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Question extends Exercise {
    private String content;

    private String correctAnswer;

    @ElementCollection
    @Column(name = "wrong_answer")
    private List<String> wrongAnswers;

    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}
