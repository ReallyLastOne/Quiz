package com.reallylastone.quiz.game.session.repository;

import com.reallylastone.quiz.game.core.quiz.model.QuizGameSession;
import com.reallylastone.quiz.game.core.translation.model.TranslationGameSession;
import com.reallylastone.quiz.game.session.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    @Query("SELECT COUNT(*) >= 1 FROM GameSession gs WHERE gs.user.id = :userId AND gs.state <> 2")
    boolean hasActiveSession(Long userId);

    @Query("SELECT COUNT(*) >= 1 FROM GameSession gs WHERE gs.user.id = :userId AND gs.state <> 2 AND TYPE(gs) = QuizGameSession")
    boolean hasActiveQuizSession(Long userId);

    @Query("SELECT COUNT(*) >= 1 FROM GameSession gs WHERE gs.user.id = :userId AND gs.state <> 2 and TYPE(gs) = TranslationGameSession")
    boolean hasActiveTranslationSession(Long userId);

    @Query(value = "SELECT COUNT(*) >= 1 FROM QUIZ_GAME_SESSION_QUESTIONS q "
            + "INNER JOIN QUIZ_GAME_SESSION qgs ON qgs.ID = q.GAME_SESSION "
            + "INNER JOIN USER_ENTITY u ON U.ID = qgs.USER_ID " + "WHERE q.STATUS = 0 "
            + "AND qgs.state <> 2", nativeQuery = true)
    boolean hasUnansweredQuestion(Long userId);

    @Query(value = "SELECT COUNT(*) >= 1 FROM TRANSLATION_GAME_SESSION_PHRASES q "
            + "INNER JOIN TRANSLATION_GAME_SESSION qgs ON qgs.ID = q.GAME_SESSION "
            + "INNER JOIN USER_ENTITY u ON U.ID = qgs.USER_ID " + "WHERE q.STATUS = 0 "
            + "AND qgs.state <> 2", nativeQuery = true)
    boolean hasUnansweredPhrases(Long id);

    @Query("SELECT gs FROM GameSession gs WHERE gs.user.id = :userId AND gs.state <> 2")
    GameSession findActive(Long userId);

    @Query("SELECT gs FROM GameSession gs WHERE gs.user.id = :userId AND gs.state <> 2 AND TYPE(gs) = QuizGameSession")
    QuizGameSession findActiveQuizGameSession(Long userId);

    @Query("SELECT gs FROM GameSession gs WHERE gs.user.id = :userId AND gs.state <> 2 AND TYPE(gs) = TranslationGameSession")
    TranslationGameSession findActiveTranslationGameSession(Long userId);

    @Query("SELECT gs FROM GameSession gs WHERE gs.user.id = :userId AND gs.state = 2 AND TYPE(gs) = QuizGameSession ORDER BY gs.finishDate LIMIT :games")
    List<QuizGameSession> findMostRecentQuizGameSessions(int games, Long userId);

    @Query("SELECT gs FROM GameSession gs WHERE gs.user.id = :userId AND gs.state = 2 AND TYPE(gs) = TranslationGameSession ORDER BY gs.finishDate LIMIT :games")
    List<TranslationGameSession> findMostRecentTranslationGameSessions(int games, Long userId);
}
