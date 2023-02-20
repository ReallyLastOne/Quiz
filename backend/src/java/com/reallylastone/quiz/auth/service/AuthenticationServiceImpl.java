package com.reallylastone.quiz.auth.service;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.AuthenticationResponse;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import com.reallylastone.quiz.auth.validation.RegisterValidator;
import com.reallylastone.quiz.configuration.security.JwtService;
import com.reallylastone.quiz.user.model.Role;
import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.user.repository.UserRepository;
import com.reallylastone.quiz.util.validation.ValidationErrorsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RegisterValidator registerValidator;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        validate(request);

        UserEntity user = UserEntity.builder().
                nickname(request.nickname()).
                email(request.email()).
                password(passwordEncoder.encode(request.password())).
                roles(Set.of(Role.USER)).
                build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken, "bearer");
    }

    private void validate(RegisterRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "registerRequest");
        registerValidator.validate(request, errors);

        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(request.nickname(), request.password()));

        var user = userRepository.findByNickname(request.nickname()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken, "bearer");
    }
}
