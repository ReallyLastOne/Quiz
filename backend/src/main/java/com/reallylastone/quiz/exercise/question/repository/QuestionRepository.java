package com.reallylastone.quiz.exercise.question.repository;

import com.reallylastone.quiz.exercise.question.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.id NOT IN :idsExcluded ORDER BY RANDOM() LIMIT 1")
    Optional<Question> findByIdNotIn(List<Long> idsExcluded);

}
