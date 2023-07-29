package com.reallylastone.cli.util;

import com.reallylastone.cli.external.model.AuthenticationRequest;
import com.reallylastone.cli.external.model.AuthenticationResponse;
import com.reallylastone.cli.external.model.PhraseCreateRequest;
import com.reallylastone.cli.external.model.PhraseView;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import java.util.List;

@Client("${quiz.api.url}")
public interface QuizHttpClient {

    @Post("/api/v1/exercises/phrases")
    HttpResponse<PhraseView> addPhrase(@Header(name = "Authorization") String authorization,
                                       @Header(value = "X-XSRF-TOKEN", name = "X-XSRF-TOKEN") String csrfToken,
                                       @CookieValue(value = "XSRF-TOKEN") String csrfCookie,
                                       @Body PhraseCreateRequest request);

    @Get("/api/v1/exercises/phrases")
    HttpResponse<List<PhraseView>> getAllPhrases(@Header(name = "Authorization") String authorization,
                                                 @Header(value = "X-XSRF-TOKEN", name = "X-XSRF-TOKEN") String csrfToken,
                                                 @CookieValue(value = "XSRF-TOKEN") String csrfCookie,
                                                 @QueryValue int page, @QueryValue int size);

    @Post("/api/v1/auth/authenticate")
    HttpResponse<AuthenticationResponse> auth(@Body AuthenticationRequest authenticationRequest,
                                              @Header(value = "X-XSRF-TOKEN", name = "X-XSRF-TOKEN") String csrfToken,
                                              @CookieValue(value = "XSRF-TOKEN") String csrfCookie);

    @Get("/api/v1/auth/csrf")
    HttpResponse<Void> csrf();
}
