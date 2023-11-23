package com.reallylastone.quiz.game.quiz.repository;

import com.reallylastone.quiz.game.quiz.model.HighscoreQuizEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighscoreQuizEntryRepository extends JpaRepository<HighscoreQuizEntry, Long> {
}
