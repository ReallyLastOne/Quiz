package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.model.QuestionExercise;
import com.reallylastone.quiz.exercise.question.repository.QuestionExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuestionExerciseServiceImpl implements QuestionExerciseService {
    private final QuestionExerciseRepository questionExerciseRepository;

    @Override
    public Optional<QuestionExercise> findById(Long id) {
        return questionExerciseRepository.findById(id);
    }

    @Override
    public Page<QuestionExercise> findAll(Pageable page) {
        if (page.getPageSize() > 100) page = PageRequest.of(page.getPageNumber(), 100);

        return questionExerciseRepository.findAll(page);
    }

    @Override
    public Optional<QuestionExercise> findRandomQuestion() {
        long count = questionExerciseRepository.count();
        int toPick = new Random().nextInt((int) count);
        Page<QuestionExercise> questionPage = questionExerciseRepository.findAll(PageRequest.of(toPick, 1));
        QuestionExercise question = null;
        if (questionPage.hasContent()) {
            question = questionPage.getContent().get(0);
        }

        return Optional.ofNullable(question);
    }
}
