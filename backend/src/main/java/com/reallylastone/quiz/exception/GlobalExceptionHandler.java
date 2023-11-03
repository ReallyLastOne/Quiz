package com.reallylastone.quiz.exception;

import com.reallylastone.quiz.util.Messages;
import com.reallylastone.quiz.util.validation.StateValidationErrorsException;
import com.reallylastone.quiz.util.validation.ValidationErrorsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final Messages messages;

    @ExceptionHandler({ ValidationErrorsException.class })
    public ResponseEntity<ErrorResponse> handle(final ValidationErrorsException e, HttpServletRequest request) {
        List<ResponseError> errors = buildResponseErrors(e.getErrors().getGlobalErrors(),
                e.getErrors().getFieldErrors());

        return unprocessableEntity(request, errors);
    }

    @ExceptionHandler({ StateValidationErrorsException.class })
    public ResponseEntity<ErrorResponse> handle(final StateValidationErrorsException e, HttpServletRequest request) {
        List<ResponseError> errors = new ArrayList<>(e.getErrors().stream()
                .map(error -> new GlobalError(messages.getMessage(error.getMessageKey(), null))).toList());

        return unprocessableEntity(request, errors);
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<ErrorResponse> handle(final AuthenticationException e, HttpServletRequest request) {
        return unprocessableEntity(request, List.of(new GlobalError(e.getMessage())));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorResponse> handle(final MethodArgumentNotValidException e, HttpServletRequest request) {
        List<ResponseError> errors = buildResponseErrors(e.getBindingResult().getGlobalErrors(),
                e.getBindingResult().getFieldErrors());

        return unprocessableEntity(request, errors);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<ErrorResponse> handle(final IllegalArgumentException e, HttpServletRequest request) {
        return unprocessableEntity(request, List.of(new GlobalError(e.getMessage())));
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorResponse> handle(final Exception e, HttpServletRequest request) {
        log.error("Unexpected error occurred: ", e);
        return internalServerError(request, List.of(new GlobalError(e.getMessage())));
    }

    private ResponseEntity<ErrorResponse> unprocessableEntity(HttpServletRequest request, List<ResponseError> errors) {
        ErrorResponse message = ErrorResponse.builder().errors(errors).instance(URI.create(request.getRequestURI()))
                .status(422).title("Invalid input data").build();

        return ResponseEntity.status(422).body(message);
    }

    private ResponseEntity<ErrorResponse> internalServerError(HttpServletRequest request, List<ResponseError> errors) {
        ErrorResponse message = ErrorResponse.builder().errors(errors).instance(URI.create(request.getRequestURI()))
                .status(500).title("Internal server error").build();

        return ResponseEntity.status(500).body(message);
    }

    private static List<ResponseError> buildResponseErrors(List<ObjectError> e,
            List<org.springframework.validation.FieldError> e1) {
        List<ResponseError> errors = new ArrayList<>();
        errors.addAll(e.stream().map(g -> new GlobalError(g.getDefaultMessage())).toList());
        errors.addAll(e1.stream().map(f -> new FieldError(f.getField(), f.getDefaultMessage())).toList());
        return errors;
    }

}
