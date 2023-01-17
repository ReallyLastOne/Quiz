package com.reallylastone.quiz.exercise.translation.service;

import com.reallylastone.quiz.exercise.translation.model.TranslationExercise;
import com.reallylastone.quiz.exercise.translation.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TranslationExerciseServiceImpl implements TranslationExerciseService {
    private final TranslationRepository translationRepository;

    @Override
    public Optional<TranslationExercise> findById(Long id) {
        return translationRepository.findById(id);
    }
}
