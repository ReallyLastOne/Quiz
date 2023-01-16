package com.reallylastone.quiz.exercise.controller;

import com.reallylastone.quiz.exercise.model.ExerciseView;
import com.reallylastone.quiz.exercise.service.ExerciseViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exercise")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseViewService exerciseViewService;

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseView> findById(@PathVariable Long id) {
        return exerciseViewService.findById(id);
    }


    @GetMapping("favicon.ico")
    void returnNoFavicon() {
    }
}
