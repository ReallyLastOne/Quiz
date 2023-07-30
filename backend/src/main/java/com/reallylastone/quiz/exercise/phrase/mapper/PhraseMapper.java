package com.reallylastone.quiz.exercise.phrase.mapper;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import com.reallylastone.quiz.user.service.UserService;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(imports = {UserService.class, BooleanUtils.class, LocalDateTime.class})
public interface PhraseMapper {
    PhraseView mapToView(Phrase exercise);

    @Mapping(target = "ownerId",
            expression = "java(BooleanUtils.isTrue(createRequest.userPhrase()) ? UserService.getCurrentUser().getId() : null)")
    @Mapping(target = "addDate", expression = "java(LocalDateTime.now())")
    Phrase mapToEntity(PhraseCreateRequest createRequest);
}
