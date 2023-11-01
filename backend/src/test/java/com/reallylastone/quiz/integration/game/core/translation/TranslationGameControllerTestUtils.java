package com.reallylastone.quiz.integration.game.core.translation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.repository.PhraseRepository;
import com.reallylastone.quiz.game.translation.model.PhraseAnswerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Locale;
import java.util.Map;

import static com.reallylastone.quiz.integration.EndpointPaths.TranslationGame.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@RequiredArgsConstructor
public class TranslationGameControllerTestUtils {
    private final PhraseRepository phraseRepository;

    private final MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    public ResultActions start(Locale sourceLanguage, Locale destinationLanguage, int phrases, String accessToken)
            throws Exception {
        return mockMvc.perform(post(START_GAME_PATH).with(csrf().asHeader())
                .header("Authorization", "Bearer " + accessToken).queryParam("phrases", String.valueOf(phrases))
                .queryParam("sourceLanguage", sourceLanguage.toLanguageTag())
                .queryParam("destinationLanguage", destinationLanguage.toLanguageTag()));
    }

    public ResultActions next(String accessToken) throws Exception {
        return mockMvc.perform(
                post(NEXT_PHRASE_PATH).with(csrf().asHeader()).header("Authorization", "Bearer " + accessToken));
    }

    public ResultActions answer(PhraseAnswerRequest request, String accessToken) throws Exception {
        return mockMvc.perform(
                post(ANSWER_PHRASE_PATH).with(csrf().asHeader()).header("Authorization", "Bearer " + accessToken)
                        .content(mapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON));
    }

    public ResultActions stop(String accessToken) throws Exception {
        return mockMvc
                .perform(post(STOP_GAME_PATH).with(csrf().asHeader()).header("Authorization", "Bearer " + accessToken));
    }

    public ResultActions findActive(String accessToken) throws Exception {
        return mockMvc.perform(
                get(FIND_ACTIVE_GAME_PATH).with(csrf().asHeader()).header("Authorization", "Bearer " + accessToken));
    }

    public ResultActions findRecent(String accessToken) throws Exception {
        return mockMvc.perform(
                get(FIND_RECENT_GAME_PATH).with(csrf().asHeader()).header("Authorization", "Bearer " + accessToken));
    }

    public void populatePhrasesFor(Long ownerId) {
        Map<Locale, String> map = Map.of(new Locale("en"), "miracle", Locale.forLanguageTag("PL"), "cud");
        Phrase phrase = new Phrase();
        phrase.setOwnerId(ownerId);
        phrase.setTranslationMap(map);
        phraseRepository.save(phrase);
    }
}
