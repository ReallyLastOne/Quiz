package com.reallylastone.quiz.integration.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reallylastone.quiz.exercise.phrase.model.CSVFileParser;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.integration.EndpointPaths;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Component
@RequiredArgsConstructor
public class PhraseControllerTestUtils {
    private final MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public ResultActions createPhrase(PhraseCreateRequest request, String accessToken) throws Exception {
        return mockMvc.perform(post(EndpointPaths.Phrase.BASE)
                .with(csrf().asHeader())
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken));
    }

    public ResultActions getAllPhrases(String accessToken, String... languages) throws Exception {
        MockHttpServletRequestBuilder builder = get(EndpointPaths.Phrase.BASE)
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken);

        if (languages != null && languages.length != 0) builder.queryParam("languages", languages);

        return mockMvc.perform(builder);
    }

    public ResultActions createPhrases(CSVFileParser parser, String accessToken, MockMultipartFile file) throws Exception {
        MockHttpServletRequestBuilder builder = multipart(EndpointPaths.Phrase.BASE)
                .file("file", file.getBytes())
                .with(csrf().asHeader())
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("Authorization", "Bearer " + accessToken);

        if (parser != null) {
            builder.content(mapper.writeValueAsString(parser));
        }

        return mockMvc.perform(builder);
    }


    @AllArgsConstructor
    @Getter
    public static class BatchPhraseCreateCase {
        private final String data;
        private final int correctPhrases;
        private final int wrongPhrases;
        private final int resultativePhrasesCount;
    }
}
