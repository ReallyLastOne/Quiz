package com.reallylastone.quiz.exercise.translation.mapper;

import com.reallylastone.quiz.exercise.translation.model.TranslationExercise;
import com.reallylastone.quiz.exercise.translation.model.TranslationExerciseResponse;
import org.mapstruct.Mapper;

@Mapper
public interface TranslationExerciseMapper {
    TranslationExerciseResponse mapToView(TranslationExercise exercise);
}
