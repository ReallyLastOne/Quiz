package com.reallylastone.quiz.exercise.question.model;

import com.reallylastone.quiz.exercise.core.Exercise;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Question extends Exercise {
    private String content;

    private String correctAnswer;

    @ElementCollection
    @Column(name = "wrong_answer")
    private List<String> wrongAnswers;

    @JoinTable(name = "question_tags",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @ManyToMany
    private List<Tag> tags = new ArrayList<>();

    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}
