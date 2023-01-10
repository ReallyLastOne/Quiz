package com.reallylastone.quiz.exercise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Exercise {
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
