package com.reallylastone.quiz.game.session.repository;

import com.reallylastone.quiz.game.session.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    @Query("select count(*) >= 1 from GameSession gs where gs.user.id = :userId and gs.finishDate is null")
    boolean hasActiveSession(Long userId);
}
