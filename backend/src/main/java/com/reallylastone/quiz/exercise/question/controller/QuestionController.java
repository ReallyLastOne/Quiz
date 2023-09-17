package com.reallylastone.quiz.exercise.question.controller;

import com.reallylastone.quiz.exercise.question.model.QuestionAddResponse;
import com.reallylastone.quiz.exercise.question.model.QuestionCreateRequest;
import com.reallylastone.quiz.exercise.question.service.QuestionViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController implements QuestionOperations {
    private final QuestionViewService questionViewService;

    @Override
    public ResponseEntity<QuestionAddResponse> create(@RequestBody QuestionCreateRequest request) {
        return questionViewService.create(request);
    }
}
