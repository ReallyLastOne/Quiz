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

import java.util.stream.Stream;

import static com.reallylastone.quiz.exercise.core.ExerciseState.*;
import static com.reallylastone.quiz.integration.EndpointPaths.QuizGame.*;
import static com.reallylastone.quiz.integration.EndpointPaths.TranslationGame.STOP_GAME_PATH;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        return Stream.of(START_GAME_PATH, NEXT_QUESTION_PATH, ANSWER_QUESTION_PATH, STOP_GAME_PATH);
    }

    private static Stream<Integer> validQuestionSize() {
        return Stream.of(1, 5, 10, 15);
    }

    private static Stream<Integer> invalidQuestionSize() {
        return Stream.of(0, -1, -20, 16, 100);
    }


    @ParameterizedTest
    @MethodSource("forbiddenPaths")
    void shouldBeForbidden(String path) throws Exception {
        mockMvc.perform(post(path)
                .with(csrf().asHeader())
        ).andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @MethodSource("validQuestionSize")
    void shouldStartGameWhenQuestionSizeProvided(Integer questionSize) throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.start(questionSize, accessToken).andExpect(status().is2xxSuccessful());
        Assertions.assertEquals(1, gameSessionRepository.findAll().size());
        Assertions.assertEquals(GameState.NEW, gameSessionRepository.findAll().get(0).getState());
        Assertions.assertEquals(QuizGameSession.class, gameSessionRepository.findAll().get(0).getClass());
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
        Assertions.assertEquals(QuizGameSession.class, gameSessionRepository.findAll().get(0).getClass());
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
        Assertions.assertEquals(NO_ANSWER, active.getQuestionsAndStatus().values().iterator().next());
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

        QuestionAnswerRequest request = new QuestionAnswerRequest("answer");
        quizUtils.answer(request, accessToken).andExpectAll(status().is2xxSuccessful(),
                jsonPath("$.questionsLeft").value(4),
                jsonPath("$.totalCorrect").value(0));
    }

    @Test
    void shouldNotProcessAnswer() throws Exception {
        quizUtils.populateQuestions();

        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        QuestionAnswerRequest request = new QuestionAnswerRequest("answer");
        quizUtils.answer(request, accessToken).andExpect(status().is4xxClientError());
    }

    @Test
    void shouldHaveWronglyAnsweredQuestion() throws Exception {
        quizUtils.populateQuestions();

        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.start(5, accessToken);
        quizUtils.next(accessToken);

        QuestionAnswerRequest request = new QuestionAnswerRequest("answer");
        quizUtils.answer(request, accessToken);

        ((QuizGameSession) gameSessionRepository.findAll().get(0)).getQuestionsAndStatus().values().forEach(e -> Assertions.assertEquals(WRONG, e));
    }

    @Test
    void shouldHaveCorrectlyAnsweredQuestion() throws Exception {
        quizUtils.populateQuestions();

        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.start(5, accessToken);
        quizUtils.next(accessToken);

        QuestionAnswerRequest request = new QuestionAnswerRequest("correct");
        quizUtils.answer(request, accessToken).andExpectAll(
                jsonPath("$.totalCorrect").value(1));

        ((QuizGameSession) gameSessionRepository.findAll().get(0)).getQuestionsAndStatus().values().forEach(e -> Assertions.assertEquals(CORRECT, e));
    }

    @Test
    void shouldCompleteGame() throws Exception {
        quizUtils.populateQuestions();

        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.start(1, accessToken);
        quizUtils.next(accessToken);

        QuestionAnswerRequest request = new QuestionAnswerRequest("correct");
        quizUtils.answer(request, accessToken).andExpectAll(jsonPath("$.questionsLeft").value(0),
                jsonPath("$.totalCorrect").value( 1));

        Assertions.assertEquals(GameState.COMPLETED, gameSessionRepository.findAll().get(0).getState());
    }

    @Test
    void shouldBeAbleToStartGameAfterCompletion() throws Exception {
        quizUtils.populateQuestions();

        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.start(1, accessToken);
        quizUtils.next(accessToken);

        QuestionAnswerRequest request = new QuestionAnswerRequest("correct");
        quizUtils.answer(request, accessToken);

        quizUtils.start(1, accessToken).andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldStopGameWhenInOne() throws Exception {
        quizUtils.populateQuestions();

        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.start(1, accessToken);
        quizUtils.next(accessToken);

        quizUtils.stop(accessToken).andExpect(status().is2xxSuccessful());
        Assertions.assertEquals(GameState.COMPLETED, gameSessionRepository.findAll().get(0).getState());
    }

    @Test
    void shouldBeOkWhenStoppingNotExistingGame() throws Exception {
        quizUtils.populateQuestions();

        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");
        quizUtils.stop(accessToken).andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldReturnActiveSessionWithNoActiveQuestion() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.start(5, accessToken);

        quizUtils.findActive(accessToken).andExpectAll(
                status().is2xxSuccessful(),
                jsonPath("$.correctAnswers").value(0),
                jsonPath("$.questionsLeft").value(5),
                jsonPath("$.currentActive").isEmpty()
        );
    }

    @Test
    void shouldReturnActiveSessionWithActiveQuestion() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");
        quizUtils.populateQuestions();
        quizUtils.start(5, accessToken);
        quizUtils.next(accessToken);

        quizUtils.findActive(accessToken).andExpectAll(
                status().is2xxSuccessful(),
                jsonPath("$.correctAnswers").value(0),
                jsonPath("$.questionsLeft").value(4),
                jsonPath("$.currentActive").exists()
        );
    }

    @Test
    void shouldReturn4xxWhenNoSession() throws Exception {
        MvcResult mvcResult = authUtils.register(new RegisterRequest("nickname", "mail@mail.com", "password")).andReturn();
        String accessToken = utils.extract(mvcResult, "accessToken");

        quizUtils.findActive(accessToken).andExpectAll(
                status().is4xxClientError()
        );
    }
}
