package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.model.QuestionExercise;
import com.reallylastone.quiz.exercise.question.repository.QuestionExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionExerciseServiceImpl implements QuestionExerciseService {
    private final QuestionExerciseRepository questionExerciseRepository;

    @Override
    public Optional<QuestionExercise> findById(Long id) {
        return questionExerciseRepository.findById(id);
    }

    @Override
    public List<QuestionExercise> findAll(Integer page, Integer pageSize) {
        page = (page > 100) ? 100 : Math.max(0, page);
        Pageable paging = PageRequest.of(page, pageSize);

        Page<QuestionExercise> pagedResult = questionExerciseRepository.findAll(paging);

        return pagedResult.getContent();
    }
}
