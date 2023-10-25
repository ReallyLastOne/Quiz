package com.reallylastone.quiz.unit.exercise;

import com.reallylastone.quiz.exercise.phrase.mapper.PhraseMapper;
import com.reallylastone.quiz.exercise.phrase.mapper.PhraseMapperImpl;
import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.user.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PhraseMapperTest {
    private final PhraseMapper phraseMapper = new PhraseMapperImpl();

    static Stream<Arguments> provideRequests() {
        Map<Locale, String> map = Map.of(Locale.US, "miracle", Locale.forLanguageTag("PL"), "cud");

        Phrase withNullOwner = new Phrase();
        withNullOwner.setTranslationMap(map);

        Phrase withOwner = new Phrase();
        withOwner.setTranslationMap(map);
        withOwner.setOwnerId(1L);

        Phrase withGlobalOwner = new Phrase();
        withGlobalOwner.setTranslationMap(map);

        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.arguments(new PhraseCreateRequest(map, null),
                        withNullOwner),
                org.junit.jupiter.params.provider.Arguments.arguments(new PhraseCreateRequest(map, true), withOwner),
                org.junit.jupiter.params.provider.Arguments.arguments(new PhraseCreateRequest(map, false),
                        withGlobalOwner));
    }

    static Stream<Arguments> provideEntities() {
        Map<Locale, String> map = Map.of(Locale.US, "miracle", Locale.forLanguageTag("PL"), "cud");

        Phrase first = new Phrase();
        first.setTranslationMap(map);
        first.setId(1L);
        first.setOwnerId(15L);
        first.setAddDate(LocalDateTime.MIN);
        first.setImagePath("/");

        Phrase second = new Phrase();
        second.setTranslationMap(map);
        second.setOwnerId(1L);
        second.setId(2L);

        Phrase third = new Phrase();
        third.setTranslationMap(map);
        third.setId(3L);
        third.setAddDate(LocalDateTime.MAX);

        return Stream.of(org.junit.jupiter.params.provider.Arguments.arguments(first, new PhraseView(1L, map)),
                org.junit.jupiter.params.provider.Arguments.arguments(second, new PhraseView(2L, map)),
                org.junit.jupiter.params.provider.Arguments.arguments(third, new PhraseView(3L, map)));
    }

    @ParameterizedTest
    @MethodSource("provideRequests")
    void shouldCorrectlyMapRequest(PhraseCreateRequest toTest, Phrase expected) {
        UserEntity user = new UserEntity();
        user.setId(1L);

        try (var userService = Mockito.mockStatic(UserService.class)) {
            userService.when(UserService::getCurrentUser).thenReturn(user);
            Phrase actual = phraseMapper.mapToEntity(toTest);

            assertThat(actual).usingRecursiveComparison().ignoringFields("addDate").isEqualTo(expected);
            assertThat(actual.getAddDate()).isNotNull();
        }
    }

    @ParameterizedTest
    @MethodSource("provideEntities")
    void shouldCorrectlyMapEntities(Phrase toTest, PhraseView expected) {
        var actual = phraseMapper.mapToView(toTest);
        assertThat(actual).isEqualTo(expected);
    }

}
