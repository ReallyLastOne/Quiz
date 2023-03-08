package com.reallylastone.quiz.auth.validation;

import com.reallylastone.quiz.auth.model.RegisterRequest;
import com.reallylastone.quiz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@Component
@RequiredArgsConstructor
public class RegisterValidator implements Validator {
    private final UserRepository userRepository;
    private final SpringValidatorAdapter validatorAdapter;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return RegisterRequest.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        RegisterRequest request = (RegisterRequest) target;

        if (validatorAdapter != null) {
            validatorAdapter.validate(request, errors);
        }

        // to prevent calling database when there are already errors
        if (!errors.hasErrors()) {
            if (userRepository.existsByEmail(request.email())) {
                errors.rejectValue("email", "invalid.email.value",
                        String.format("Email '%s' already in use", request.email()));
            }
        }

        if (!errors.hasErrors()) {
            if (userRepository.existsByNickname(request.nickname())) {
                errors.rejectValue("email", "invalid.nickname.value",
                        String.format("Nickname '%s' already in use", request.nickname()));
            }
        }


    }
}
