package com.reallylastone.quiz.exercise.question.repository;

import com.reallylastone.quiz.exercise.question.model.QuestionExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionExerciseRepository extends JpaRepository<QuestionExercise, Long> {
}
