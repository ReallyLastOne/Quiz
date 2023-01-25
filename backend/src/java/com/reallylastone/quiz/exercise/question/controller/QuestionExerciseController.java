package com.reallylastone.quiz.exercise.question.controller;

import com.reallylastone.quiz.exercise.question.model.QuestionExerciseView;
import com.reallylastone.quiz.exercise.question.service.QuestionExerciseViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises/questions")
@RequiredArgsConstructor
public class QuestionExerciseController {
    private final QuestionExerciseViewService questionExerciseViewService;

    @GetMapping("/{id}")
    public ResponseEntity<QuestionExerciseView> findById(@PathVariable Long id) {
        return questionExerciseViewService.findById(id);
    }

    @GetMapping
    public ResponseEntity<List<QuestionExerciseView>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                              @RequestParam(defaultValue = "100") Integer pageSize) {
        return questionExerciseViewService.findAll(page, pageSize);
    }

    @GetMapping("/random")
    public ResponseEntity<QuestionExerciseView> findRandomQuestion() {
        return questionExerciseViewService.findRandomQuestion();
    }

    @GetMapping("favicon.ico")
    void returnNoFavicon() {
    }
}
