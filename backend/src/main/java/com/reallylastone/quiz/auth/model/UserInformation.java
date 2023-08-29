package com.reallylastone.quiz.auth.model;

import com.reallylastone.quiz.user.model.Role;
import com.reallylastone.quiz.user.model.UserEntity;

import java.util.Set;

public record UserInformation(Long id, String username, Set<Role> roles) {
    public UserInformation(UserEntity user) {
        this(user.getId(), user.getUsername(), user.getRoles());
    }
}
