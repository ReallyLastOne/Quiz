package com.reallylastone.quiz.integration.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reallylastone.quiz.exercise.question.model.QuestionCreateRequest;
import com.reallylastone.quiz.exercise.question.repository.QuestionRepository;
import com.reallylastone.quiz.integration.AbstractIntegrationTest;
import com.reallylastone.quiz.integration.IntegrationTestUtils;
import com.reallylastone.quiz.integration.auth.AuthenticationControllerTestUtils;
import com.reallylastone.quiz.tag.model.Tag;
import com.reallylastone.quiz.tag.repository.TagRepository;
import com.reallylastone.quiz.user.model.Role;
import com.reallylastone.quiz.user.model.UserEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
class QuestionControllerTest extends AbstractIntegrationTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationControllerTestUtils authenticationUtils;
    @Autowired
    private IntegrationTestUtils generalUtils;
    @Autowired
    private QuestionControllerTestUtils questionControllerTestUtils;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TagRepository tagRepository;

    private static Stream<QuestionCreateRequest> correctCreateQuestionRequests() {
        return Stream.of(new QuestionCreateRequest(List.of(""), List.of(""), "question?", List.of()),
                new QuestionCreateRequest(List.of("", ""), List.of(""), "question?", List.of()),
                new QuestionCreateRequest(List.of(""), List.of("", ""), "question?", List.of()),
                new QuestionCreateRequest(List.of("", ""), List.of("", ""), "question?", List.of()));
    }

    private static Stream<QuestionCreateRequest> wrongCreateQuestionRequests() {
        return Stream.of(new QuestionCreateRequest(null, null, null, null),
                new QuestionCreateRequest(new ArrayList<>(), null, null, null),
                new QuestionCreateRequest(null, new ArrayList<>(), null, null),
                new QuestionCreateRequest(List.of(""), List.of(""), "", null),
                new QuestionCreateRequest(List.of(), null, null, null),
                new QuestionCreateRequest(List.of(), null, null, null),
                new QuestionCreateRequest(List.of(""), List.of(""), "question?", List.of("not existing tag")));
    }

    @ParameterizedTest
    @MethodSource("correctCreateQuestionRequests")
    void shouldCreateQuestion(QuestionCreateRequest request) throws Exception {
        MvcResult mvcResult = authenticationUtils.register().andReturn();

        UserEntity user = userRepository.findAll().get(0);
        user.setRoles(Set.of(Role.ADMIN));
        userRepository.save(user);

        questionControllerTestUtils.createQuestion(request, generalUtils.extract(mvcResult, "accessToken"))
                .andExpect(status().is2xxSuccessful());

        Assertions.assertEquals(1, questionRepository.findAll().size());
    }

    @ParameterizedTest
    @MethodSource("wrongCreateQuestionRequests")
    void shouldNotCreateQuestion(QuestionCreateRequest request) throws Exception {
        MvcResult mvcResult = authenticationUtils.register().andReturn();

        UserEntity user = userRepository.findAll().get(0);
        user.setRoles(Set.of(Role.ADMIN));
        userRepository.save(user);

        questionControllerTestUtils.createQuestion(request, generalUtils.extract(mvcResult, "accessToken"))
                .andExpect(status().is4xxClientError());

        Assertions.assertEquals(0, questionRepository.findAll().size());
    }

    @Test
    void shouldNotCreateQuestion() throws Exception {
        MvcResult mvcResult = authenticationUtils.register().andReturn();

        questionControllerTestUtils
                .createQuestion(new QuestionCreateRequest(List.of("", ""), List.of(""), "question?", List.of()),
                        generalUtils.extract(mvcResult, "accessToken"))
                .andExpect(status().is4xxClientError());

        Assertions.assertEquals(0, questionRepository.findAll().size());
    }

    @Test
    void shouldCreateQuestionWithTag() throws Exception {
        Tag tag = new Tag();
        tag.setName("Linux");

        tagRepository.save(tag);
        MvcResult mvcResult = authenticationUtils.register().andReturn();

        UserEntity user = userRepository.findAll().get(0);
        user.setRoles(Set.of(Role.ADMIN));
        userRepository.save(user);

        questionControllerTestUtils.createQuestion(
                new QuestionCreateRequest(List.of("", ""), List.of("", ""), "question?", List.of("Linux")),
                generalUtils.extract(mvcResult, "accessToken")).andExpect(status().is2xxSuccessful());
        Assertions.assertEquals(1, questionRepository.findAll().size());
    }
}
