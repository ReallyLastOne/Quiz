package com.reallylastone.quiz.exercise.question.repository;

import com.reallylastone.quiz.exercise.question.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
