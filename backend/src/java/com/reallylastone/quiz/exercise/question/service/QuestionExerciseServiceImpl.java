package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.model.QuestionExercise;
import com.reallylastone.quiz.exercise.question.repository.QuestionExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionExerciseServiceImpl implements QuestionExerciseService {
    private final QuestionExerciseRepository questionExerciseRepository;

    @Override
    public Optional<QuestionExercise> findById(Long id) {
        return questionExerciseRepository.findById(id);
    }
}
