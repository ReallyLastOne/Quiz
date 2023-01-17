package com.reallylastone.quiz.exercise.translation.repository;

import com.reallylastone.quiz.exercise.translation.model.TranslationExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends JpaRepository<TranslationExercise, Long> {
}
