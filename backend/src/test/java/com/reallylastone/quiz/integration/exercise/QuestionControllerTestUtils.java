package com.reallylastone.quiz.integration.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reallylastone.quiz.exercise.question.model.QuestionCreateRequest;
import com.reallylastone.quiz.integration.EndpointPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@RequiredArgsConstructor
public class QuestionControllerTestUtils {
    private final MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public ResultActions createQuestion(QuestionCreateRequest request, String accessToken) throws Exception {
        return mockMvc.perform(
                post(EndpointPaths.Question.BASE).with(csrf().asHeader()).content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + accessToken));
    }
}
