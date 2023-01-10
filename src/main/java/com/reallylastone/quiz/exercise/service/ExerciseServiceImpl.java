package com.reallylastone.quiz.exercise.service;

import com.reallylastone.quiz.exercise.model.Exercise;
import com.reallylastone.quiz.exercise.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;

    @Override
    public Optional<Exercise> findById(Long id) {
        return exerciseRepository.findById(id);
    }
}
