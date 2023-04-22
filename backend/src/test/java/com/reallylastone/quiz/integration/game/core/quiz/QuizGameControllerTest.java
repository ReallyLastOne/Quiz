package com.reallylastone.quiz.integration.game.core.quiz;

import com.reallylastone.quiz.auth.model.RegisterRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.game.core.quiz.model.QuizGameSession;
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

import java.util.Arrays;
import java.util.stream.Stream;

import static com.reallylastone.quiz.integration.EndpointPaths.QuizGame.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
class QuizGameControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationControllerTestUtils authUtils;

    @Autowired
    private QuizGameControllerTestUtils quizUtils;

    @Autowired
    private IntegrationTestUtils utils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    private static Stream<String> forbiddenPaths() {
        return Stream.of(START_GAME_PATH, NEXT_QUESTION_PATH, ANSWER_QUESTION_PATH);
    }

    private static Stream<Integer> validQuestionSize() {
        return Stream.of(1, 50, 99, 111);
    }

    private static Stream<Integer> invalidQuestionSize() {
        return Stream.of(0, -1, -20);
    }


    @ParameterizedTest
    @MethodSource("forbiddenPaths")
    void shouldBeForbidden(String path) throws Exception {
        mockMvc.perform(post(path)).andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource("validQuestionSize")
    void shouldStartGameWhenQuestionSizeProvided(Integer questionSize) throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.start(questionSize, accessToken).andExpect(status().is2xxSuccessful());
        Assertions.assertEquals(1, gameSessionRepository.findAll().size());
        Assertions.assertEquals(GameState.NEW, gameSessionRepository.findAll().get(0).getState());
    }

    @ParameterizedTest
    @MethodSource("invalidQuestionSize")
    void shouldNotStartGameBecauseQuestionSizeInvalid(Integer questionSize) throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.start(questionSize, accessToken).andExpect(status().isUnprocessableEntity());
        Assertions.assertEquals(0, gameSessionRepository.findAll().size());
    }

    @Test
    void shouldNotBeAbleToStartSecondGame() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.start(5, accessToken);

        quizUtils.start(5, accessToken).andExpect(status().isUnprocessableEntity());
        Assertions.assertEquals(1, gameSessionRepository.findAll().size());
        Assertions.assertEquals(GameState.NEW, gameSessionRepository.findAll().get(0).getState());
    }

    @Test
    void shouldGetQuestion() throws Exception {
        quizUtils.populateQuestions();

        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.start(5, accessToken);
        quizUtils.next(accessToken).andExpect(status().is2xxSuccessful());
        Assertions.assertEquals(1, gameSessionRepository.findAll().size());
        Assertions.assertEquals(GameState.IN_PROGRESS, gameSessionRepository.findAll().get(0).getState());

        QuizGameSession active = (QuizGameSession) gameSessionRepository.findAll().get(0);
        Assertions.assertEquals(1, active.getQuestionsAndStatus().size());
        Assertions.assertNull(active.getQuestionsAndStatus().values().iterator().next());
    }

    @Test
    void shouldNotGetQuestionBecauseNotInGame() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.next(accessToken).andExpect(status().is4xxClientError());
    }

    @Test
    void shouldProcessAnswer() throws Exception {
        quizUtils.populateQuestions();

        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.start(5, accessToken);
        quizUtils.next(accessToken).andExpect(status().is2xxSuccessful());

        System.out.println(gameSessionRepository.findAll());
        QuestionAnswerRequest request = new QuestionAnswerRequest("answer");
        quizUtils.answer(request, accessToken).andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldNotProcessAnswer() throws Exception {
        quizUtils.populateQuestions();

        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        QuestionAnswerRequest request = new QuestionAnswerRequest("answer");
        quizUtils.answer(request, accessToken).andExpect(status().is4xxClientError());
    }

}
