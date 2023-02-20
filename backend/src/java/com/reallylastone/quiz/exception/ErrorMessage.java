package com.reallylastone.quiz.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.Map;

@Builder
record ErrorMessage(URI type, String title, HttpStatus status, String detail, URI instance,
                    Map<String, Object> parameters) {
}
