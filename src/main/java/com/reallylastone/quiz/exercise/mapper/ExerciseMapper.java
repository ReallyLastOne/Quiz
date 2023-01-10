package com.reallylastone.quiz.exercise.mapper;

import com.reallylastone.quiz.exercise.model.Exercise;
import com.reallylastone.quiz.exercise.model.ExerciseView;
import org.mapstruct.Mapper;

@Mapper
public interface ExerciseMapper {
    ExerciseView mapToView(Exercise exercise);
}
