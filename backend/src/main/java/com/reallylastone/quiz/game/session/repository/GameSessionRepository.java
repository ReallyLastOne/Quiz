package com.reallylastone.quiz.game.session.repository;

import com.reallylastone.quiz.game.core.quiz.model.QuizGameSession;
import com.reallylastone.quiz.game.session.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    @Query("select count(*) >= 1 from GameSession gs where gs.user.id = :userId and gs.finishDate is null")
    boolean hasActiveSession(Long userId);

    @Query("select count(*) >= 1 from GameSession gs where gs.user.id = :userId and gs.finishDate is null and type(gs) = QuizGameSession")
    boolean hasActiveQuizSession(Long userId);

    @Query("select count(*) >= 1 from GameSession gs where gs.user.id = :userId and gs.finishDate is null and type(gs) = TranslationGameSession")
    boolean hasActiveTranslationSession(Long userId);

    @Query(value =
            "SELECT COUNT(*) >= 1 FROM QUIZ_GAME_SESSIONS_QUESTIONS q " +
            "INNER JOIN QUIZ_GAME_SESSION qgs ON qgs.ID = q.GAME_SESSION " +
            "INNER JOIN USER_ENTITY u ON U.ID = qgs.USER_ID " +
            "WHERE q.STATUS IS NULL",
            nativeQuery = true)
    boolean hasUnansweredQuestion(Long userId);

    @Query("select gs from GameSession gs where gs.user.id = :userId and gs.finishDate is null and type(gs) = QuizGameSession")
    QuizGameSession findActive(Long userId);
}
