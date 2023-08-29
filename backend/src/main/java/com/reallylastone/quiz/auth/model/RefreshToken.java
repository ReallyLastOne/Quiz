package com.reallylastone.quiz.auth.model;

import com.reallylastone.quiz.user.model.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_generator")
    @SequenceGenerator(name = "refresh_token_generator", sequenceName = "refresh_token_seq", allocationSize = 1)
    private Long id;

    private LocalDateTime expirationDate;

    private UUID uuid;

    @OneToOne
    private UserEntity user;
}
