package com.reallylastone.cli.util;

import com.reallylastone.cli.external.model.AuthenticationRequest;
import com.reallylastone.cli.external.model.AuthenticationResponse;
import com.reallylastone.cli.external.model.PhraseCreateRequest;
import com.reallylastone.cli.external.model.PhraseView;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client("${quiz.api.url}")
public interface QuizHttpClient {

    @Post("/api/v1/exercises/phrases")
    PhraseView addPhrase(@Header(name = "Authorization") String authorization,
                         @Body PhraseCreateRequest request);

    @Post("/api/v1/auth/authenticate")
    AuthenticationResponse auth(@Body AuthenticationRequest authenticationRequest);
}
