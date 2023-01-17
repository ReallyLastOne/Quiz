package com.reallylastone.quiz.exercise.question.controller;

import com.reallylastone.quiz.exercise.question.model.QuestionExerciseView;
import com.reallylastone.quiz.exercise.question.service.QuestionExerciseViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exercise/question")
@RequiredArgsConstructor
public class QuestionExerciseController {
    private final QuestionExerciseViewService questionExerciseViewService;

    @GetMapping("/{id}")
    public ResponseEntity<QuestionExerciseView> findById(@PathVariable Long id) {
        return questionExerciseViewService.findById(id);
    }

    @GetMapping("favicon.ico")
    void returnNoFavicon() {
    }
}
