package com.reallylastone.cli.command.phrase;

import com.reallylastone.cli.external.model.AuthenticationResponse;
import com.reallylastone.cli.external.model.PhraseCreateRequest;
import com.reallylastone.cli.external.model.PhraseView;
import com.reallylastone.cli.util.QuizHttpClient;
import com.reallylastone.cli.util.TokenManager;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.cookie.Cookie;
import jakarta.inject.Inject;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Command(name = "add", mixinStandardHelpOptions = true, version = "1.0")
@Introspected
public class AddPhraseCommand implements Runnable {
    private static final List<String> USE_EXAMPLES = Arrays.asList("phrase add it=ciao pl=cześć", "phrase add en=\"thank you\" it=grazie fr=merci", "phrase add es=\"buenos dias\"");
    private final TokenManager tokenManager = new TokenManager();
    @CommandLine.Parameters(converter = StringToLocaleConverter.class, arity = "1", description = "Phrase translations in format: en=hello es=hola. Unlimited translations can be provided.")
    private Map<Locale, String> translations;
    @CommandLine.Option(names = {"--usage", "-u"}, help = true, description = "Print some examples of usage and exit.")
    private boolean usage;
    @CommandLine.Option(names = {"--list", "-l"}, help = true, description = "Print all possible locales that can be used in traslation and exit.")
    private boolean locales;
    @Inject
    private QuizHttpClient client;

    @Override
    public void run() {
        if (helpOptionsHandled(usage, locales)) return;

        try {
            Optional<Cookie> cookie = client.csrf().getCookie("XSRF-TOKEN");
            if (cookie.isEmpty()) {
                System.out.println("Something went wrong...");
                return;
            }

            AuthenticationResponse local = tokenManager.read();
            var request = new PhraseCreateRequest(translations, true);
            System.out.println("Sending phrase to the backend service...");

            var response = client.addPhrase("Bearer %s".formatted(local.accessToken()), cookie.get().getValue(), cookie.get().getValue(), request);
            Optional<PhraseView> body = response.getBody();
            body.ifPresent(phraseView -> System.out.printf("Successfuly added phrase: %s%n", phraseView.translationMap()));
        } catch (HttpClientResponseException e) {
            System.err.println("error " + e.getResponse().toString() + ", status " + e.getStatus().getCode() + ", reason " + e.getStatus().getReason());
        } catch (IOException e) {
            System.err.println("Could not fetch access token from local file storage, please login via 'login' command");
        }
    }


    private boolean helpOptionsHandled(boolean usage, boolean locales) {
        if (usage) {
            USE_EXAMPLES.forEach(System.out::println);

            return true;
        }

        if (locales) {
            Arrays.stream(Locale.getISOLanguages()).map(Locale::new).forEach(locale -> System.out.printf("%s %s%n", locale.toLanguageTag(), locale.getDisplayLanguage(Locale.ENGLISH)));

            return true;
        }

        return false;
    }

    static class StringToLocaleConverter implements CommandLine.ITypeConverter<Locale> {
        @Override
        public Locale convert(String value) {
            return Locale.forLanguageTag(value);
        }
    }
}
