package com.reallylastone.quiz.exercise.question.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class QuestionExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String question;

    private String correctAnswer;

    @ElementCollection
    @Column(name = " wrong_answer")
    private List<String> wrongAnswers;
}
