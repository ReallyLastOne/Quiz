package com.reallylastone.quiz.integration.game.core.quiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static com.reallylastone.quiz.integration.EndpointPaths.QuizGame.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@RequiredArgsConstructor
public class QuizGameControllerTestUtils {
    private final QuestionRepository questionRepository;

    private final MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    public ResultActions start(int questions, String accessToken) throws Exception {
        return mockMvc.perform(post(START_GAME_PATH).with(csrf().asHeader())
                .header("Authorization", "Bearer " + accessToken).queryParam("questions", String.valueOf(questions)));
    }

    public ResultActions next(String accessToken) throws Exception {
        return mockMvc.perform(
                post(NEXT_QUESTION_PATH).with(csrf().asHeader()).header("Authorization", "Bearer " + accessToken));
    }

    public ResultActions answer(QuestionAnswerRequest request, String accessToken) throws Exception {
        return mockMvc.perform(
                post(ANSWER_QUESTION_PATH).with(csrf().asHeader()).header("Authorization", "Bearer " + accessToken)
                        .content(mapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON));
    }

    public ResultActions stop(String accessToken) throws Exception {
        return mockMvc
                .perform(post(STOP_GAME_PATH).with(csrf().asHeader()).header("Authorization", "Bearer " + accessToken));
    }

    public ResultActions findActive(String accessToken) throws Exception {
        return mockMvc.perform(
                post(FIND_ACTIVE_GAME_PATH).with(csrf().asHeader()).header("Authorization", "Bearer " + accessToken));
    }

    public void populateQuestions() {
        Question question = new Question();
        question.setContent("question?");
        question.setCorrectAnswer("correct");
        question.setCorrectAnswers(Arrays.asList("first", "second", "third"));

        questionRepository.save(question);
    }
}
