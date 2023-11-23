package com.reallylastone.quiz.game.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HighscoreRefreshTask {
    @PersistenceContext
    private EntityManager entityManager;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    @Transactional
    void refreshViews() {
        // to avoid installation of extension for psql
        entityManager.createNativeQuery("REFRESH MATERIALIZED VIEW quiz_highscore_view").executeUpdate();
        entityManager.createNativeQuery("REFRESH MATERIALIZED VIEW translation_highscore_view").executeUpdate();
    }

}
