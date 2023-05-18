package com.reallylastone.quiz.integration.auth;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.RefreshTokenRequest;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private IntegrationTestUtils generalUtils;

    private static Stream<RegisterRequest> wrongRegisterRequests() {
        return Stream.of(
                new RegisterRequest("", "email@gmail.com", "password"),
                new RegisterRequest("nickname", "", "password"),
                new RegisterRequest("nickname", "email@gmail.com", ""),
                new RegisterRequest("nickname", "email", "password"),
                new RegisterRequest("nickname", "2", "password")
        );
    }

    private static Stream<RegisterRequest> correctRegisterRequests() {
        return Stream.of(
                new RegisterRequest("nickname", "email@gmail.com", "password")
        );
    }

    @ParameterizedTest
    @MethodSource("correctRegisterRequests")
    void shouldRegisterUser(RegisterRequest request) throws Exception {
        controllerUtils.register(request).andExpect(status().isOk());

        Assertions.assertEquals(1, userRepository.findAll().size());
        Assertions.assertEquals(1, refreshTokenRepository.findAll().size());
    }

    @ParameterizedTest
    @MethodSource("wrongRegisterRequests")
    void shouldNotRegisterUser(RegisterRequest request) throws Exception {
        controllerUtils.register(request).andExpect(status().isUnprocessableEntity());

        Assertions.assertEquals(0, userRepository.findAll().size());
        Assertions.assertEquals(0, refreshTokenRepository.findAll().size());
    }

    @Test
    void shouldNotAuthenticateUser() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("notExistingUser", "password");
        controllerUtils.authenticate(request).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldAuthenticateUser() throws Exception {
        RegisterRequest request = new RegisterRequest("existingUser", "email@gmail.com", "password");
        controllerUtils.register(request);

        AuthenticationRequest authRequest = new AuthenticationRequest("existingUser", "password");
        controllerUtils.authenticate(authRequest).andExpect(status().is2xxSuccessful());

        Assertions.assertEquals(2, refreshTokenRepository.findAll().size());
    }

    @Test
    void shouldGetAccessToken() throws Exception {
        RegisterRequest request = new RegisterRequest("existingUser", "email@gmail.com", "password");

        MvcResult mvcResult = controllerUtils.register(request).andReturn();
        String refreshToken = generalUtils.extract(mvcResult, "refreshToken");

        RefreshTokenRequest refreshRequest = new RefreshTokenRequest(refreshToken);
        controllerUtils.refresh(refreshRequest).andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldNotGetAccessTokenBecauseRefreshTokenExpired() throws Exception {
        RegisterRequest request = new RegisterRequest("existingUser", "email@gmail.com", "password");
        MvcResult mvcResult = controllerUtils.register(request).andReturn();
        refreshTokenRepository.findAll().get(0).setExpirationDate(LocalDateTime.now().minusDays(1));

        String refreshToken = generalUtils.extract(mvcResult, "refreshToken");
        RefreshTokenRequest refreshRequest = new RefreshTokenRequest(refreshToken);
        controllerUtils.refresh(refreshRequest).andExpect(status().isUnprocessableEntity());
    }
}
