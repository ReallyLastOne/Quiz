package com.reallylastone.quiz.game.session.repository;

import com.reallylastone.quiz.game.session.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
}
