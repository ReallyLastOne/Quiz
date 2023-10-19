package com.reallylastone.quiz.configuration.exception;

import com.reallylastone.quiz.util.validation.StateValidationErrorsException;
import com.reallylastone.quiz.util.validation.ValidationErrorsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
// based on https://github.com/VictorKrapivin/jsr-vs-spring-validation
public class GlobalExceptionHandler {
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorMessage> handle(final ConstraintViolationException e, HttpServletRequest request) {
        List<InvalidInputDataErrors.FieldError> errors = InvalidInputDataErrors.from(e).getErrors();
        return createErrorMessage(request, errors);
    }

    @ExceptionHandler({ValidationErrorsException.class})
    public ResponseEntity<ErrorMessage> handle(final ValidationErrorsException e, HttpServletRequest request) {
        List<InvalidInputDataErrors.FieldError> errors = InvalidInputDataErrors.from(e).getErrors();
        return createErrorMessage(request, errors);
    }

    @ExceptionHandler({StateValidationErrorsException.class})
    public ResponseEntity<ErrorMessage> handle(final StateValidationErrorsException e, HttpServletRequest request) {
        return createErrorMessage(request, e.getErrors());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessage> handle(final Exception e, HttpServletRequest request) {
        log.error("Unexpected error occurred: ", e);
        return createErrorMessage(request, List.of(e.getMessage()));
    }

    private ResponseEntity<ErrorMessage> createErrorMessage(HttpServletRequest request, List<?> errors) {
        ErrorMessage message = ErrorMessage.builder().parameters(buildParams(errors)).
                instance(URI.create(request.getRequestURI())).
                status(HttpStatus.UNPROCESSABLE_ENTITY).title("Invalid input data").
                build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
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

        static InvalidInputDataErrors from(ConstraintViolationException e) {
            return new InvalidInputDataErrors(e.getConstraintViolations().stream().map(constraintViolation -> new FieldError(cutOffMethodAndParameterName(constraintViolation), constraintViolation.getMessage())).toList());
        }

        private static String cutOffMethodAndParameterName(ConstraintViolation<?> violation) {
            String pathAsString = violation.getPropertyPath().toString();
            if (violation.getExecutableParameters() != null) {
                int payloadPathStartIndex = pathAsString.indexOf('.', pathAsString.indexOf('.') + 1);
                return pathAsString.substring(payloadPathStartIndex + 1);
            } else {
                return pathAsString;
            }
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
