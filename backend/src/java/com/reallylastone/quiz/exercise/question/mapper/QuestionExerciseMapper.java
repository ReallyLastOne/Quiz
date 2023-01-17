package com.reallylastone.quiz.exercise.question.mapper;

import com.reallylastone.quiz.exercise.question.model.QuestionExercise;
import com.reallylastone.quiz.exercise.question.model.QuestionExerciseView;
import org.mapstruct.Mapper;

@Mapper
public interface QuestionExerciseMapper {
    QuestionExerciseView mapToView(QuestionExercise questionExercise);
}
