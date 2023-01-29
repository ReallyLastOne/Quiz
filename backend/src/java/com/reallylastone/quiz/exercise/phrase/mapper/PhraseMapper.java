package com.reallylastone.quiz.exercise.phrase.mapper;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import org.mapstruct.Mapper;

@Mapper
public interface PhraseMapper {
    PhraseView mapToView(Phrase exercise);

    Phrase mapToEntity(PhraseCreateRequest createRequest);
}
