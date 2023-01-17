package com.reallylastone.quiz.exercise.translation.controller;

import com.reallylastone.quiz.exercise.translation.model.TranslationExerciseView;
import com.reallylastone.quiz.exercise.translation.service.TranslationExerciseViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exercise/translation")
@RequiredArgsConstructor
public class TranslationExerciseController {
    private final TranslationExerciseViewService translationViewService;

    @GetMapping("/{id}")
    public ResponseEntity<TranslationExerciseView> findById(@PathVariable Long id) {
        return translationViewService.findById(id);
    }

    @GetMapping("favicon.ico")
    void returnNoFavicon() {
    }
}
