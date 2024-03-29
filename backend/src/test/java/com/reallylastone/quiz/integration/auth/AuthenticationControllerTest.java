package com.reallylastone.quiz.integration.auth;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import com.reallylastone.quiz.auth.repository.RefreshTokenRepository;
import com.reallylastone.quiz.integration.AbstractIntegrationTest;
import com.reallylastone.quiz.integration.IntegrationTestUtils;
import com.reallylastone.quiz.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.reallylastone.quiz.integration.EndpointPaths.Authentication.CSRF_PATH;
import static com.reallylastone.quiz.integration.EndpointPaths.Authentication.ME_PATH;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
class AuthenticationControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AuthenticationControllerTestUtils controllerUtils;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private IntegrationTestUtils generalUtils;

    private static Stream<RegisterRequest> wrongRegisterRequests() {
        return Stream.of(new RegisterRequest("", "email@gmail.com", "password"),
                new RegisterRequest("nickname", "", "password"), new RegisterRequest("nickname", "email@gmail.com", ""),
                new RegisterRequest("nickname", "email", "password"), new RegisterRequest("nickname", "2", "password"));
    }

    private static Stream<RegisterRequest> correctRegisterRequests() {
        return Stream.of(new RegisterRequest("nickname", "email@gmail.com", "password"));
    }

    @ParameterizedTest
    @MethodSource("correctRegisterRequests")
    void shouldRegisterUser(RegisterRequest request) throws Exception {
        controllerUtils.register(request).andExpect(status().isOk()).andExpect(header().exists("Set-Cookie"));

        Assertions.assertEquals(1, userRepository.findAll().size());
        Assertions.assertEquals(1, refreshTokenRepository.findAll().size());
    }

    @ParameterizedTest
    @MethodSource("wrongRegisterRequests")
    void shouldNotRegisterUser(RegisterRequest request) throws Exception {
        controllerUtils.register(request).andExpect(status().isUnprocessableEntity())
                .andExpect(header().doesNotExist("Set-Cookie"));

        Assertions.assertEquals(0, userRepository.findAll().size());
        Assertions.assertEquals(0, refreshTokenRepository.findAll().size());
    }

    @Test
    void shouldNotAuthenticateUser() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("notExistingUser", "password");
        controllerUtils.authenticate(request).andExpect(status().isUnprocessableEntity())
                .andExpect(header().doesNotExist("Set-Cookie"));
    }

    @Test
    void shouldAuthenticateUser() throws Exception {
        RegisterRequest request = new RegisterRequest("existingUser", "email@gmail.com", "password");
        controllerUtils.register(request);

        AuthenticationRequest authRequest = new AuthenticationRequest("existingUser", "password");
        controllerUtils.authenticate(authRequest).andExpect(status().is2xxSuccessful())
                .andExpect(header().exists("Set-Cookie"));

        Assertions.assertEquals(2, refreshTokenRepository.findAll().size());
    }

    @Test
    void shouldGetAccessToken() throws Exception {
        RegisterRequest request = new RegisterRequest("existingUser", "email@gmail.com", "password");

        MvcResult mvcResult = controllerUtils.register(request).andReturn();
        String refreshToken = generalUtils.extract(mvcResult, "refreshToken");

        controllerUtils.refresh(refreshToken).andExpect(status().is2xxSuccessful())
                .andExpect(header().doesNotExist("Set-Cookie"));
    }

    @Test
    void shouldNotGetAccessTokenBecauseNoCookie() throws Exception {
        controllerUtils.refresh(null).andExpect(status().is4xxClientError());
    }

    @Test
    void shouldNotGetAccessTokenBecauseWrongRefreshToken() throws Exception {
        controllerUtils.refresh("definitely wrong").andExpect(status().is4xxClientError());
    }

    @Test
    void shouldNotGetAccessTokenBecauseRefreshTokenExpired() throws Exception {
        RegisterRequest request = new RegisterRequest("existingUser", "email@gmail.com", "password");
        MvcResult mvcResult = controllerUtils.register(request).andReturn();
        refreshTokenRepository.findAll().get(0).setExpirationDate(LocalDateTime.now().minusDays(1));

        String refreshToken = generalUtils.extract(mvcResult, "refreshToken");
        controllerUtils.refresh(refreshToken).andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldReturnCsrfToken() throws Exception {
        // TODO: remove @DirtiesContext annotation - context need to be deleted to pass the test, don't know why
        mockMvc.perform(MockMvcRequestBuilders.get(CSRF_PATH)).andExpectAll(status().is2xxSuccessful(),
                cookie().exists("XSRF-TOKEN"));
    }

    @Test
    void shouldBeForbidden() throws Exception {
        mockMvc.perform(post(ME_PATH).with(csrf().asHeader())).andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnMe() throws Exception {
        RegisterRequest request = new RegisterRequest("existingUser", "email@gmail.com", "password");
        MvcResult mvcResult = controllerUtils.register(request).andReturn();

        mockMvc.perform(get(ME_PATH).header("Authorization", "Bearer " + generalUtils.extract(mvcResult, "accessToken"))
                .contentType(MediaType.ALL).with(csrf().asHeader())).andExpectAll(status().is2xxSuccessful(),
                        jsonPath("$.username").value("existingUser"), jsonPath("$.roles", hasSize(1)));
    }
}
