package com.reallylastone.quiz.user.service;

import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsManager {
    private final UserRepository userRepository;

    /**
     * Gets currently authenticated user
     *
     * @return currently authenticated user
     */
    public static UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByNickname(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void createUser(UserDetails user) {
        // TODO
    }

    @Override
    public void updateUser(UserDetails user) {
        // TODO
    }

    @Override
    public void deleteUser(String username) {
        // TODO
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // TODO
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByNickname(username);
    }
}
