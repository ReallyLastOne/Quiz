package com.reallylastone.quiz.game.translation.repository;

import com.reallylastone.quiz.game.translation.model.HighscoreTranslationEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighscoreTranslationEntryRepository extends JpaRepository<HighscoreTranslationEntry, Long> {
}
