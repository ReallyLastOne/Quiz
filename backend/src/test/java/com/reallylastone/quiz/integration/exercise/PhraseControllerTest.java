package com.reallylastone.quiz.integration.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.repository.PhraseRepository;
import com.reallylastone.quiz.integration.AbstractIntegrationTest;
import com.reallylastone.quiz.integration.EndpointPaths;
import com.reallylastone.quiz.integration.IntegrationTestUtils;
import com.reallylastone.quiz.integration.auth.AuthenticationControllerTestUtils;
import com.reallylastone.quiz.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
class PhraseControllerTest extends AbstractIntegrationTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhraseRepository phraseRepository;
    @Autowired
    private AuthenticationControllerTestUtils controllerUtils;
    @Autowired
    private IntegrationTestUtils generalUtils;

    private static Stream<PhraseCreateRequest> correctCreatePhraseRequests() {
        return Stream.of(
                new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko"), null),
                new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko"), true),
                new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko", Locale.GERMAN, "apfel"), false)
        );
    }

    private static Stream<PhraseCreateRequest> wrongCreatePhraseRequests() {
        return Stream.of(
                new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple"), null),
                new PhraseCreateRequest(Map.of(), true),
                new PhraseCreateRequest(null, false)
        );
    }

    private static Stream<Arguments> phrasesToMergeData() {
        return Stream.of(
                Arguments.of((Object) new PhraseCreateRequest[]{
                        new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple"), false),
                        new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko"), false)
                }),
                Arguments.of((Object) new PhraseCreateRequest[]{
                        new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", Locale.GERMAN, "apfel"), false),
                        new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko"), false)
                })
        );
    }

    @ParameterizedTest
    @MethodSource("correctCreatePhraseRequests")
    void shouldCreatePhrase(PhraseCreateRequest request) throws Exception {
        MvcResult mvcResult = controllerUtils.register().andReturn();

        mockMvc.perform(post(EndpointPaths.Phrase.BASE)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generalUtils.extract(mvcResult, "accessToken")))
                .andExpect(status().is2xxSuccessful());

        Assertions.assertEquals(1, phraseRepository.findAll().size());
    }

    @ParameterizedTest
    @MethodSource("wrongCreatePhraseRequests")
    void shouldNotCreatePhrase(PhraseCreateRequest request) throws Exception {
        MvcResult mvcResult = controllerUtils.register().andReturn();

        mockMvc.perform(post(EndpointPaths.Phrase.BASE)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generalUtils.extract(mvcResult, "accessToken")))
                .andExpect(status().is4xxClientError());

        Assertions.assertEquals(0, phraseRepository.findAll().size());
    }

    @ParameterizedTest
    @MethodSource("phrasesToMergeData")
    void shouldMergePhrases(PhraseCreateRequest[] requests) throws Exception {
        MvcResult mvcResult = controllerUtils.register().andReturn();

        mockMvc.perform(post(EndpointPaths.Phrase.BASE)
                .content(mapper.writeValueAsString(requests[0]))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + generalUtils.extract(mvcResult, "accessToken")));

        mockMvc.perform(post(EndpointPaths.Phrase.BASE)
                        .content(mapper.writeValueAsString(requests[1]))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generalUtils.extract(mvcResult, "accessToken")))
                .andExpect(status().is2xxSuccessful());

        Assertions.assertEquals(1, phraseRepository.findAll().size());
    }

    @Test
    void shouldCreateSamePhraseBecauseDifferentUsers() throws Exception {
        MvcResult firstUserRegister = controllerUtils.register().andReturn();
        MvcResult secondUserRegister = controllerUtils.register(new RegisterRequest("e", "ee@mail.com", "eee")).andReturn();

        PhraseCreateRequest request = new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko"), true);

        mockMvc.perform(post(EndpointPaths.Phrase.BASE)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + generalUtils.extract(firstUserRegister, "accessToken")));

        mockMvc.perform(post(EndpointPaths.Phrase.BASE)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generalUtils.extract(secondUserRegister, "accessToken")))
                .andExpect(status().is2xxSuccessful());

        Assertions.assertEquals(2, phraseRepository.findAll().size());
    }

    @Test
    void shouldNotCreatePhraseNorMerge() throws Exception {
        MvcResult mvcResult = controllerUtils.register().andReturn();

        PhraseCreateRequest first = new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko"), true);
        PhraseCreateRequest second = new PhraseCreateRequest(Map.of(Locale.ENGLISH, "unambiguous", new Locale("pl"), "jednoznaczny"), true);
        PhraseCreateRequest repeated = new PhraseCreateRequest(Map.of(Locale.ENGLISH, "unambiguous", new Locale("pl"), "jabłko"), true);

        mockMvc.perform(post(EndpointPaths.Phrase.BASE)
                .content(mapper.writeValueAsString(first))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + generalUtils.extract(mvcResult, "accessToken")));

        mockMvc.perform(post(EndpointPaths.Phrase.BASE)
                        .content(mapper.writeValueAsString(second))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generalUtils.extract(mvcResult, "accessToken")))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(post(EndpointPaths.Phrase.BASE)
                        .content(mapper.writeValueAsString(repeated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generalUtils.extract(mvcResult, "accessToken")))
                .andExpect(status().is4xxClientError());
    }

}
