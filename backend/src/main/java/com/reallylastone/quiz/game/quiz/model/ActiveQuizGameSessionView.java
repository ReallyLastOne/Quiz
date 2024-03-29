package com.reallylastone.quiz.game.quiz.model;

import com.reallylastone.quiz.exercise.ExerciseState;
import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionView;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public record ActiveQuizGameSessionView(int correctAnswers, int questionsLeft, QuestionView currentActive) {

    public ActiveQuizGameSessionView(QuizGameSession session, Function<Question, QuestionView> mapper) {
        this((int) session.countOf(ExerciseState.CORRECT), session.leftQuestions(), getView(session, mapper));
    }

    private static QuestionView getView(QuizGameSession session, Function<Question, QuestionView> mapper) {
        Optional<Map.Entry<Question, ExerciseState>> current = session.findCurrent();
        return current.map(questionExerciseStateEntry -> mapper.apply(questionExerciseStateEntry.getKey()))
                .orElse(null);
    }
}
