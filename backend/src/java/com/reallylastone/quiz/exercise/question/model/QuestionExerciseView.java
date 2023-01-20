package com.reallylastone.quiz.exercise.question.model;

import lombok.Data;

import java.util.List;

@Data
public class QuestionExerciseView {
    private String question;

    private String correctAnswer;

    private List<String> wrongAnswers;
}
