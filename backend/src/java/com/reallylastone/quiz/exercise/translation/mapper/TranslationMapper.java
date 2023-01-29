package com.reallylastone.quiz.exercise.translation.mapper;

import com.reallylastone.quiz.exercise.translation.model.Translation;
import com.reallylastone.quiz.exercise.translation.model.TranslationCreateRequest;
import com.reallylastone.quiz.exercise.translation.model.TranslationView;
import org.mapstruct.Mapper;

@Mapper
public interface TranslationMapper {
    TranslationView mapToView(Translation exercise);

    Translation mapToEntity(TranslationCreateRequest createRequest);
}
