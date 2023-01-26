package com.reallylastone.quiz.exercise.question.mapper;

import com.reallylastone.quiz.exercise.question.model.QuestionExercise;
import com.reallylastone.quiz.exercise.question.model.QuestionExerciseResponse;
import org.mapstruct.Mapper;

@Mapper
public interface QuestionExerciseMapper {
    QuestionExerciseResponse mapToView(QuestionExercise questionExercise);
}
