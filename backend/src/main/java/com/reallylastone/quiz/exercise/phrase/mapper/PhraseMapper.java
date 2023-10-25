package com.reallylastone.quiz.exercise.phrase.mapper;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCSVEntry;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import com.reallylastone.quiz.user.service.UserService;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Mapper(imports = { UserService.class, BooleanUtils.class, LocalDateTime.class })
public interface PhraseMapper {
    PhraseView mapToView(Phrase exercise);

    @Mapping(target = "ownerId", expression = "java(BooleanUtils.isTrue(createRequest.userPhrase()) ? UserService.getCurrentUser().getId() : null)")
    @Mapping(target = "addDate", expression = "java(LocalDateTime.now())")
    Phrase mapToEntity(PhraseCreateRequest createRequest);

    default List<Phrase> mapToEntities(List<PhraseCSVEntry> entries) {
        return entries.stream().map(phraseCSVEntry -> {
            Phrase phrase = new Phrase();
            phrase.setTranslationMap(
                    IntStream.range(0, phraseCSVEntry.languageHeaders().size()).boxed().collect(Collectors.toMap(
                            i -> new Locale(phraseCSVEntry.languageHeaders().get(i)), phraseCSVEntry.words()::get)));
            return phrase;
        }).toList();

    }
}
