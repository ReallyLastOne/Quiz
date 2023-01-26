package com.reallylastone.quiz.exercise.question.model;

import lombok.Data;

import java.util.List;

@Data
public class QuestionExerciseResponse {
    private String question;

    private String correctAnswer;

    private List<String> wrongAnswers;
}
