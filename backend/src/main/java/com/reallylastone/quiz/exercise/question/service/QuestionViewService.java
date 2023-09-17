package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.controller.QuestionAddResponse;
import com.reallylastone.quiz.exercise.question.model.QuestionCreateRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionViewService {
    ResponseEntity<QuestionView> findById(Long id);

    ResponseEntity<List<QuestionView>> findAll(Pageable page);

    ResponseEntity<QuestionView> findRandomQuestion();

    ResponseEntity<QuestionAddResponse> create(QuestionCreateRequest request);
}
