package com.reallylastone.quiz.auth.service;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.AuthenticationResponse;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import com.reallylastone.quiz.configuration.security.JwtService;
import com.reallylastone.quiz.user.model.Role;
import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.user.repository.UserRepository;
import com.reallylastone.quiz.util.validation.ValidationUtils;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        validate(request);

        UserEntity user = UserEntity.builder().
                nickname(request.getNickname()).
                email(request.getEmail()).
                password(passwordEncoder.encode(request.getPassword())).
                roles(Set.of(Role.USER)).
                build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().accessToken(jwtToken).tokenType("bearer").build();
    }

    private void validate(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new ConstraintViolationException(ValidationUtils.createConstraintViolationSet("email", "email is already used"));
        if (userRepository.existsByNickname(request.getNickname()))
            throw new ConstraintViolationException(ValidationUtils.createConstraintViolationSet("nickname", "nickname is already used"));
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(request.getNickname(), request.getPassword()));

        var user = userRepository.findByNickname(request.getNickname()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().accessToken(jwtToken).tokenType("bearer").build();
    }
}
