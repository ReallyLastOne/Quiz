package com.reallylastone.quiz.exercise.question.controller;

import com.reallylastone.quiz.exercise.question.service.QuestionViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController implements QuestionOperations {
    private final QuestionViewService questionViewService;
}
