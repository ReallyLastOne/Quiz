package com.reallylastone.quiz.integration.game.core.quiz;

import com.reallylastone.quiz.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class QuizGameControllerTest extends AbstractIntegrationTest {
    private static final String testPath = "/api/v1/game/quiz";
    private static final String startGamePath = testPath + "/startgame";
    private static final String nextQuestionPath = testPath + "/next";
    private static final String answerQuestionPath = testPath + "/answer";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldBeForbidden1() throws Exception {
        mockMvc.perform(post(startGamePath)).andExpect(status().isForbidden());
    }

    @Test
    void shouldBeForbidden2() throws Exception {
        mockMvc.perform(post(nextQuestionPath)).andExpect(status().isForbidden());
    }

    @Test
    void shouldBeForbidden3() throws Exception {
        mockMvc.perform(post(answerQuestionPath)).andExpect(status().isForbidden());
    }
}
