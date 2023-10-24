package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Optional<Question> findById(Long id);

    Page<Question> findAll(Pageable pageable);

    Question findRandomQuestion(List<Long> idsExcluded);

    Question findRandomQuestion();

    Question create(Question request);
}
