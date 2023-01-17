package com.reallylastone.quiz.exercise.question.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class QuestionExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String question;

    private String correctAnswer;

    private String wrongAnswer1;

    private String wrongAnswer2;

    private String wrongAnswer3;

    private String wrongAnswer4;
}
