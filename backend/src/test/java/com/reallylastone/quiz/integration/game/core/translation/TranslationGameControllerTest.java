package com.reallylastone.quiz.integration.game.core.translation;

import com.reallylastone.quiz.auth.model.RegisterRequest;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.core.translation.model.TranslationGameSession;
import com.reallylastone.quiz.game.session.model.GameState;
import com.reallylastone.quiz.game.session.repository.GameSessionRepository;
import com.reallylastone.quiz.integration.AbstractIntegrationTest;
import com.reallylastone.quiz.integration.IntegrationTestUtils;
import com.reallylastone.quiz.integration.auth.AuthenticationControllerTestUtils;
import com.reallylastone.quiz.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Locale;
import java.util.stream.Stream;

import static com.reallylastone.quiz.exercise.core.ExerciseState.*;
import static com.reallylastone.quiz.integration.EndpointPaths.TranslationGame.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
class TranslationGameControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationControllerTestUtils authUtils;

    @Autowired
    private TranslationGameControllerTestUtils translationUtils;

    @Autowired
    private IntegrationTestUtils utils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    private static Stream<String> forbiddenPaths() {
        return Stream.of(START_GAME_PATH, NEXT_PHRASE_PATH, ANSWER_PHRASE_PATH, STOP_GAME_PATH);
    }

    private static Stream<Integer> validPhrasesSize() {
        return Stream.of(1, 15);
    }

    private static Stream<Integer> invalidPhrasesSize() {
        return Stream.of(0, -1, -20, 16, 100);
    }

    @ParameterizedTest
    @MethodSource("forbiddenPaths")
    void shouldBeForbidden(String path) throws Exception {
        mockMvc.perform(post(path).with(csrf().asHeader())).andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource("validPhrasesSize")
    void shouldStartGameWhenPhrasesSizeProvided(Integer phrasesSize) throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        translationUtils.start(new Locale("en"), new Locale("pl"), phrasesSize, accessToken)
                .andExpect(status().is2xxSuccessful());
        Assertions.assertEquals(1, gameSessionRepository.findAll().size());
        Assertions.assertEquals(GameState.NEW, gameSessionRepository.findAll().get(0).getState());
        Assertions.assertEquals(TranslationGameSession.class, gameSessionRepository.findAll().get(0).getClass());
    }

    @ParameterizedTest
    @MethodSource("invalidPhrasesSize")
    void shouldNotStartGameBecausePhraseSizeInvalid(Integer phrasesSize) throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        translationUtils.start(new Locale("en"), new Locale("pl"), phrasesSize, accessToken)
                .andExpect(status().isUnprocessableEntity());
        Assertions.assertEquals(0, gameSessionRepository.findAll().size());
    }

    @Test
    void shouldNotBeAbleToStartSecondGame() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        translationUtils.start(new Locale("en"), new Locale("pl"), 5, accessToken);

        translationUtils.start(new Locale("en"), new Locale("pl"), 5, accessToken)
                .andExpect(status().isUnprocessableEntity());
        Assertions.assertEquals(1, gameSessionRepository.findAll().size());
        Assertions.assertEquals(GameState.NEW, gameSessionRepository.findAll().get(0).getState());
    }

    @Test
    void shouldGetPhrase() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");
        translationUtils.populatePhrasesFor(userRepository.findAll().get(0).getId());

        translationUtils.start(new Locale("en"), new Locale("pl"), 5, accessToken);
        ResultActions next = translationUtils.next(accessToken);
        next.andExpect(status().is2xxSuccessful());
        Assertions.assertEquals(1, gameSessionRepository.findAll().size());
        Assertions.assertEquals(GameState.IN_PROGRESS, gameSessionRepository.findAll().get(0).getState());

        TranslationGameSession active = (TranslationGameSession) gameSessionRepository.findAll().get(0);
        Assertions.assertEquals(1, active.getTranslationsAndStatus().size());
        Assertions.assertEquals(NO_ANSWER, active.getTranslationsAndStatus().values().iterator().next());
    }

    @Test
    void shouldNotGetPhraseBecauseNotInGame() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        translationUtils.next(accessToken).andExpect(status().is4xxClientError());
    }

    @Test
    void shouldProcessAnswer() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");
        translationUtils.populatePhrasesFor(userRepository.findAll().get(0).getId());

        translationUtils.start(new Locale("en"), new Locale("pl"), 5, accessToken);
        translationUtils.next(accessToken).andExpect(status().is2xxSuccessful());

        PhraseAnswerRequest request = new PhraseAnswerRequest("answer");
        translationUtils.answer(request, accessToken).andExpectAll(status().is2xxSuccessful(),
                jsonPath("$.phrasesLeft").value(4));
    }

    @Test
    void shouldNotProcessAnswer() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        PhraseAnswerRequest request = new PhraseAnswerRequest("answer");
        translationUtils.answer(request, accessToken).andExpect(status().is4xxClientError());
    }

    @Test
    void shouldHaveWronglyAnsweredPhrase() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");
        translationUtils.populatePhrasesFor(userRepository.findAll().get(0).getId());

        translationUtils.start(new Locale("en"), new Locale("pl"), 5, accessToken);
        translationUtils.next(accessToken);

        PhraseAnswerRequest request = new PhraseAnswerRequest("answer");
        translationUtils.answer(request, accessToken);

        ((TranslationGameSession) gameSessionRepository.findAll().get(0)).getTranslationsAndStatus().values()
                .forEach(e -> Assertions.assertEquals(WRONG, e));
    }

    @Test
    void shouldHaveCorrectlyAnsweredPhrase() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");
        translationUtils.populatePhrasesFor(userRepository.findAll().get(0).getId());

        translationUtils.start(new Locale("en"), new Locale("pl"), 5, accessToken);
        translationUtils.next(accessToken);

        PhraseAnswerRequest request = new PhraseAnswerRequest("cud");
        translationUtils.answer(request, accessToken);

        ((TranslationGameSession) gameSessionRepository.findAll().get(0)).getTranslationsAndStatus().values()
                .forEach(e -> Assertions.assertEquals(CORRECT, e));
    }

    @Test
    void shouldCompleteGame() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");
        translationUtils.populatePhrasesFor(userRepository.findAll().get(0).getId());

        translationUtils.start(new Locale("en"), new Locale("pl"), 1, accessToken);
        translationUtils.next(accessToken);

        PhraseAnswerRequest request = new PhraseAnswerRequest("correct");
        translationUtils.answer(request, accessToken).andExpectAll(jsonPath("$.phrasesLeft").value(0));

        Assertions.assertEquals(GameState.COMPLETED, gameSessionRepository.findAll().get(0).getState());
    }

    @Test
    void shouldBeAbleToStartGameAfterCompletion() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");
        translationUtils.populatePhrasesFor(userRepository.findAll().get(0).getId());

        translationUtils.start(new Locale("en"), new Locale("pl"), 1, accessToken);
        translationUtils.next(accessToken);

        PhraseAnswerRequest request = new PhraseAnswerRequest("correct");
        translationUtils.answer(request, accessToken);

        translationUtils.start(new Locale("en"), new Locale("pl"), 1, accessToken)
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldStopGameWhenInOne() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");
        translationUtils.populatePhrasesFor(userRepository.findAll().get(0).getId());

        translationUtils.start(new Locale("en"), new Locale("pl"), 1, accessToken);
        translationUtils.next(accessToken);

        translationUtils.stop(accessToken).andExpect(status().is2xxSuccessful());
        Assertions.assertEquals(GameState.COMPLETED, gameSessionRepository.findAll().get(0).getState());
    }

    @Test
    void shouldBeOkWhenStoppingNotExistingGame() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");
        translationUtils.stop(accessToken).andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldReturnActiveSessionWithNoActivePhrase() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        translationUtils.start(new Locale("en"), new Locale("pl"), 5, accessToken);

        translationUtils.findActive(accessToken).andExpectAll(status().is2xxSuccessful(),
                jsonPath("$.correctAnswers").value(0), jsonPath("$.phrasesLeft").value(5),
                jsonPath("$.currentActive").isEmpty());
    }

    @Test
    void shouldReturnActiveSessionWithActivePhrase() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");
        translationUtils.populatePhrasesFor(userRepository.findAll().get(0).getId());
        translationUtils.start(new Locale("en"), new Locale("pl"), 5, accessToken);
        translationUtils.next(accessToken);

        translationUtils.findActive(accessToken).andExpectAll(status().is2xxSuccessful(),
                jsonPath("$.correctAnswers").value(0), jsonPath("$.phrasesLeft").value(4),
                jsonPath("$.currentActive").exists());
    }

    @Test
    void shouldReturn4xxWhenNoSession() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        translationUtils.findActive(accessToken).andExpectAll(status().is4xxClientError());
    }

    @Test
    void shouldNotFindRecent() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        translationUtils.findRecent(accessToken).andExpectAll(status().is4xxClientError());
    }

    @Test
    void shouldFindRecent() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password"))
                .andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");
        translationUtils.populatePhrasesFor(userRepository.findAll().get(0).getId());

        translationUtils.start(new Locale("en"), new Locale("pl"), 1, accessToken);
        translationUtils.next(accessToken);

        PhraseAnswerRequest request = new PhraseAnswerRequest("correct");
        translationUtils.answer(request, accessToken);

        translationUtils.findRecent(accessToken).andExpectAll(status().is2xxSuccessful());
    }
}
