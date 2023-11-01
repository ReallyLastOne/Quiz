package com.reallylastone.quiz.exception;

import com.reallylastone.quiz.util.Messages;
import com.reallylastone.quiz.util.validation.StateValidationErrorsException;
import com.reallylastone.quiz.util.validation.ValidationErrorsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
// based on https://github.com/VictorKrapivin/jsr-vs-spring-validation
public class GlobalExceptionHandler {
    private final Messages messages;

    @ExceptionHandler({ ValidationErrorsException.class })
    public ResponseEntity<ErrorMessage> handle(final ValidationErrorsException e, HttpServletRequest request) {
        List<InvalidInputDataErrors.FieldError> errors = InvalidInputDataErrors.from(e).getErrors();
        return unprocessableEntity(request, errors);
    }

    @ExceptionHandler({ StateValidationErrorsException.class })
    public ResponseEntity<ErrorMessage> handle(final StateValidationErrorsException e, HttpServletRequest request) {
        return unprocessableEntity(request,
                e.getErrors().stream().map(error -> messages.getMessage(error.getMessageKey(), null)).toList());
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<ErrorMessage> handle(final AuthenticationException e, HttpServletRequest request) {
        return unprocessableEntity(request, Collections.singletonList(e.getMessage()));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorMessage> handle(final MethodArgumentNotValidException e, HttpServletRequest request) {
        return unprocessableEntity(request, Collections.singletonList(e.getParameter() + ":" + e.getBody()));
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<ErrorMessage> handle(final IllegalArgumentException e, HttpServletRequest request) {
        return unprocessableEntity(request, Collections.singletonList(e.getMessage()));
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorMessage> handle(final Exception e, HttpServletRequest request) {
        log.error("Unexpected error occurred: ", e);
        return internalServerError(request, List.of(e.getMessage()));
    }

    private ResponseEntity<ErrorMessage> unprocessableEntity(HttpServletRequest request, List<?> errors) {
        ErrorMessage message = ErrorMessage.builder().parameters(buildParams(errors))
                .instance(URI.create(request.getRequestURI())).status(HttpStatus.UNPROCESSABLE_ENTITY)
                .title("Invalid input data").build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
    }

    private ResponseEntity<ErrorMessage> internalServerError(HttpServletRequest request, List<?> errors) {
        ErrorMessage message = ErrorMessage.builder().parameters(buildParams(errors))
                .instance(URI.create(request.getRequestURI())).status(HttpStatus.INTERNAL_SERVER_ERROR)
                .title("Internal server error").build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    private Map<String, Object> buildParams(List<?> errors) {
        Map<String, Object> params = new HashMap<>();
        params.put("errors", errors);

        return params;
    }

    @Getter
    public static class InvalidInputDataErrors {
        private final List<FieldError> errors;

        private InvalidInputDataErrors(List<FieldError> errors) {
            this.errors = errors;
        }

        static InvalidInputDataErrors from(ValidationErrorsException e) {
            Errors errors = e.getErrors();
            List<org.springframework.validation.FieldError> source = errors.getFieldErrors();
            List<FieldError> result = new ArrayList<>();
            for (org.springframework.validation.FieldError fieldError : source) {
                result.add(new FieldError(fieldError.getField(), fieldError.getDefaultMessage()));
            }
            return new InvalidInputDataErrors(result);
        }

        @AllArgsConstructor
        @Getter
        @Setter
        public static class FieldError {
            private String field;
            private String message;
        }
    }
}
