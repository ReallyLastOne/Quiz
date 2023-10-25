package com.reallylastone.quiz.integration.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.reallylastone.quiz.integration.EndpointPaths.Authentication.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@RequiredArgsConstructor
public class AuthenticationControllerTestUtils {
    private final MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Provides the result of a user registration request
     *
     * @param request
     *            register user request
     *
     * @return result of registration user request
     *
     * @throws Exception
     *             if a problem occurred in performing request or parsing sending object
     */
    public ResultActions register(RegisterRequest request) throws Exception {
        return mockMvc.perform(post(REGISTER_PATH).with(csrf().asHeader()).content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Provides the result of a default user registration request. Default user is
     *
     * @return result of registration user request
     *
     * @throws Exception
     *             if a problem occurred in performing request or parsing sending object
     */
    public ResultActions register() throws Exception {
        return register(new RegisterRequest("nickname", "mail@mail.com", "password"));
    }

    /**
     * Provides the result of a user authentication request
     *
     * @param request
     *            authentication user request
     *
     * @return result of authentication user request
     *
     * @throws Exception
     *             if a problem occurred in performing request or parsing sending object
     */
    public ResultActions authenticate(AuthenticationRequest request) throws Exception {
        return mockMvc.perform(post(AUTH_PATH).with(csrf().asHeader()).content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Provides the result of a refresh token request
     *
     * @return result of refresh token request
     *
     * @throws Exception
     *             if a problem occurred in performing request or parsing sending object
     */
    public ResultActions refresh(String refreshToken) throws Exception {
        return mockMvc.perform(post(REFRESH_PATH).with(csrf().asHeader())
                .cookie(new Cookie("refresh_token", refreshToken)).contentType(MediaType.APPLICATION_JSON));
    }

}
