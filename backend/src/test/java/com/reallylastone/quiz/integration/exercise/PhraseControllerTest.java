package com.reallylastone.quiz.integration.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.repository.PhraseRepository;
import com.reallylastone.quiz.integration.AbstractIntegrationTest;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private AuthenticationControllerTestUtils authenticationUtils;
    @Autowired
    private IntegrationTestUtils generalUtils;
    @Autowired
    private PhraseControllerTestUtils phraseControllerTestUtils;

    private static Stream<PhraseCreateRequest> correctCreatePhraseRequests() {
        return Stream.of(new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko"), null), new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko"), true), new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko", Locale.GERMAN, "apfel"), false));
    }

    private static Stream<PhraseCreateRequest> wrongCreatePhraseRequests() {
        return Stream.of(new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple"), null), new PhraseCreateRequest(Map.of(), true), new PhraseCreateRequest(null, false));
    }

    private static Stream<Arguments> phrasesToMergeData() {
        return Stream.of(Arguments.of((Object) new PhraseCreateRequest[]{new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple"), false), new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko"), false)}), Arguments.of((Object) new PhraseCreateRequest[]{new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", Locale.GERMAN, "apfel"), false), new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko"), false)}));
    }

    @ParameterizedTest
    @MethodSource("correctCreatePhraseRequests")
    void shouldCreatePhrase(PhraseCreateRequest request) throws Exception {
        MvcResult mvcResult = authenticationUtils.register().andReturn();

        phraseControllerTestUtils.createPhrase(request, generalUtils.extract(mvcResult, "accessToken"))
                .andExpect(status().is2xxSuccessful());

        Assertions.assertEquals(1, phraseRepository.findAll().size());
    }

    @ParameterizedTest
    @MethodSource("wrongCreatePhraseRequests")
    void shouldNotCreatePhrase(PhraseCreateRequest request) throws Exception {
        MvcResult mvcResult = authenticationUtils.register().andReturn();

        phraseControllerTestUtils.createPhrase(request, generalUtils.extract(mvcResult, "accessToken"))
                .andExpect(status().is4xxClientError());

        Assertions.assertEquals(0, phraseRepository.findAll().size());
    }

    @ParameterizedTest
    @MethodSource("phrasesToMergeData")
    void shouldMergePhrases(PhraseCreateRequest[] requests) throws Exception {
        MvcResult mvcResult = authenticationUtils.register().andReturn();

        phraseControllerTestUtils.createPhrase(requests[0], generalUtils.extract(mvcResult, "accessToken"));

        phraseControllerTestUtils.createPhrase(requests[1], generalUtils.extract(mvcResult, "accessToken"))
                .andExpect(status().is2xxSuccessful());

        Assertions.assertEquals(1, phraseRepository.findAll().size());
    }

    @Test
    void shouldCreateSamePhraseBecauseDifferentUsers() throws Exception {
        MvcResult firstUserRegister = authenticationUtils.register().andReturn();
        MvcResult secondUserRegister = authenticationUtils.register(new RegisterRequest("e", "ee@mail.com", "eee")).andReturn();

        PhraseCreateRequest request = new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko"), true);

        phraseControllerTestUtils.createPhrase(request, generalUtils.extract(firstUserRegister, "accessToken"));

        phraseControllerTestUtils.createPhrase(request, generalUtils.extract(secondUserRegister, "accessToken"))
                .andExpect(status().is2xxSuccessful());

        Assertions.assertEquals(2, phraseRepository.findAll().size());
    }

    @Test
    void shouldNotCreatePhraseNorMerge() throws Exception {
        MvcResult mvcResult = authenticationUtils.register().andReturn();

        PhraseCreateRequest first = new PhraseCreateRequest(Map.of(Locale.ENGLISH, "apple", new Locale("pl"), "jabłko"), true);
        PhraseCreateRequest second = new PhraseCreateRequest(Map.of(Locale.ENGLISH, "unambiguous", new Locale("pl"), "jednoznaczny"), true);
        PhraseCreateRequest repeated = new PhraseCreateRequest(Map.of(Locale.ENGLISH, "unambiguous", new Locale("pl"), "jabłko"), true);

        phraseControllerTestUtils.createPhrase(first, generalUtils.extract(mvcResult, "accessToken"))
                .andExpect(status().is2xxSuccessful());

        phraseControllerTestUtils.createPhrase(second, generalUtils.extract(mvcResult, "accessToken"))
                .andExpect(status().is2xxSuccessful());

        phraseControllerTestUtils.createPhrase(repeated, generalUtils.extract(mvcResult, "accessToken"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnOwnPhrases() throws Exception {
        MvcResult mvcResult = authenticationUtils.register().andReturn();

        Phrase ownEntity = new Phrase();
        // TODO: it looks like @Transactional annotation on test methods does not clean sequences value, so we pick user id this way
        // TODO: see if we can achieve sequences cleanup also
        Long userId = userRepository.findAll().get(0).getId();
        ownEntity.setOwnerId(userId);
        phraseRepository.save(ownEntity);

        Phrase other = new Phrase();
        other.setOwnerId(-1L);
        phraseRepository.save(other);

        phraseControllerTestUtils.getAllPhrases(generalUtils.extract(mvcResult, "accessToken"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)));
    }

}
