package com.reallylastone.quiz.exception;

import lombok.Builder;

import java.net.URI;
import java.util.List;

@Builder
public record ErrorResponse(URI type, String title, int status, String detail, URI instance,
        List<ResponseError> errors) {
}
