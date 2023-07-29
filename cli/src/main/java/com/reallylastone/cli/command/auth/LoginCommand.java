package com.reallylastone.cli.command.auth;

import com.reallylastone.cli.external.model.AuthenticationRequest;
import com.reallylastone.cli.external.model.AuthenticationResponse;
import com.reallylastone.cli.util.QuizHttpClient;
import com.reallylastone.cli.util.TokenManager;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.cookie.Cookie;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Optional;

@Command(name = "login")
@Introspected
public class LoginCommand implements Runnable {
    private final TokenManager tokenManager = new TokenManager();
    @Option(names = {"-l", "--login"}, required = true)
    private String login;
    @Option(names = {"-p", "--password"}, required = true)
    private String password;
    @Inject
    private QuizHttpClient client;

    @Override
    public void run() {
        try {
            HttpResponse<Void> csrf = client.csrf();
            Optional<Cookie> cookie = csrf.getCookie("XSRF-TOKEN");

            if (cookie.isEmpty()) {
                System.out.println("Something went wrong...");
                return;
            }

            HttpResponse<AuthenticationResponse> response = client.auth(new AuthenticationRequest(login, password), cookie.get().getValue(), cookie.get().getValue());
            Optional<AuthenticationResponse> body = response.getBody();
            if (body.isPresent()) {
                System.out.println("Successfully logged in");
                tokenManager.store(body.get());
            }
        } catch (Exception e) {
            System.err.println("Unknown error occurred " + e.getMessage());
        }
    }
}
