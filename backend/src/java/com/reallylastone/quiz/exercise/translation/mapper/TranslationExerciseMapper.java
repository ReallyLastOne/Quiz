package com.reallylastone.quiz.exercise.translation.mapper;

import com.reallylastone.quiz.exercise.translation.model.TranslationExercise;
import com.reallylastone.quiz.exercise.translation.model.TranslationExerciseView;
import org.mapstruct.Mapper;

@Mapper
public interface TranslationExerciseMapper {
    TranslationExerciseView mapToView(TranslationExercise exercise);
}
