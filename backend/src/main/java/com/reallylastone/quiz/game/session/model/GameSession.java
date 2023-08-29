package com.reallylastone.quiz.game.session.model;

import com.reallylastone.quiz.user.model.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_session_generator")
    @SequenceGenerator(name = "game_session_generator", sequenceName = "game_session_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startDate;

    private LocalDateTime finishDate;

    private GameState state = GameState.NEW;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public void finish() {
        state = GameState.COMPLETED;
    }
}
