package com.reallylastone.quiz.auth.service;

import com.reallylastone.quiz.auth.model.AuthenticationRequest;
import com.reallylastone.quiz.auth.model.AuthenticationResponse;
import com.reallylastone.quiz.auth.model.RegisterRequest;
import com.reallylastone.quiz.configuration.security.JwtService;
import com.reallylastone.quiz.user.model.Role;
import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.user.repository.UserRepository;
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
        UserEntity user = UserEntity.builder().
                nickname(request.getNickname()).
                email(request.getEmail()).
                password(passwordEncoder.encode(request.getPassword())).
                roles(Set.of(Role.USER)).
                build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(request.getNickname(), request.getPassword()));

        var user = userRepository.findByNickname(request.getNickname()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
