package com.reallylastone.cli.command.phrase;

import com.reallylastone.cli.external.model.AuthenticationResponse;
import com.reallylastone.cli.external.model.PhraseView;
import com.reallylastone.cli.util.QuizHttpClient;
import com.reallylastone.cli.util.TokenManager;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.cookie.Cookie;
import jakarta.inject.Inject;
import picocli.CommandLine;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CommandLine.Command(name = "list", mixinStandardHelpOptions = true, version = "1.0")
public class GetAllPhrasesCommand implements Runnable {
    private final TokenManager tokenManager = new TokenManager();
    @CommandLine.Option(names = {"-p", "--page"}, description = "Page to search. Default: 0")
    private int page = 0;
    @CommandLine.Option(names = {"-n", "--size"}, description = "Results on a page. Default: 100")
    private int size = 100;
    @Inject
    private QuizHttpClient client;

    @Override
    public void run() {
        try {
            Optional<Cookie> cookie = client.csrf().getCookie("XSRF-TOKEN");
            if (cookie.isEmpty()) {
                System.out.println("Something went wrong...");
                return;
            }

            AuthenticationResponse local = tokenManager.read();
            System.out.println("Sending phrase to the backend service...");
            var response = client.getAllPhrases("Bearer " + local.accessToken(), cookie.get().getValue(), cookie.get().getValue(), page, size);
            Optional<List<PhraseView>> body = response.getBody();
            body.ifPresent(phraseViews -> System.out.printf("Fetched phrases: %s %n Size: %s%n", phraseViews, phraseViews.size()));
        } catch (HttpClientResponseException e) {
            System.err.println("error " + e.getResponse().toString() + ", status " + e.getStatus().getCode() + ", reason " + e.getStatus().getReason());
        } catch (IOException e) {
            System.err.println("Could not fetch access token from local file storage, please login via 'login' command");
        }
    }
}