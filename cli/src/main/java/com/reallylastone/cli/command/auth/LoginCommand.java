package com.reallylastone.cli.command.auth;

import com.reallylastone.cli.external.model.AuthenticationRequest;
import com.reallylastone.cli.external.model.AuthenticationResponse;
import com.reallylastone.cli.util.QuizHttpClient;
import com.reallylastone.cli.util.TokenWriter;
import io.micronaut.core.annotation.Introspected;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "login")
@Introspected
public class LoginCommand implements Runnable {
    private final TokenWriter tokenWriter = new TokenWriter();
    @Option(names = {"-l", "--login"}, required = true)
    private String login;
    @Option(names = {"-p", "--password"}, required = true)
    private String password;
    @Inject
    private QuizHttpClient client;

    @Override
    public void run() {
        try {
            AuthenticationResponse response = client.auth(new AuthenticationRequest(login, password));
            System.out.println("Successfully logged in");
            new Thread(() -> tokenWriter.store(response)).start();
        } catch (Exception e) {
            System.err.println("Unknown error occurred " + e.getMessage());
        }
    }
}
