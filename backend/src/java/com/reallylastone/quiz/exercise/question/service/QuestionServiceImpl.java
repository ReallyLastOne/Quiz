package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Page<Question> findAll(Pageable page) {
        if (page.getPageSize() > 100) page = PageRequest.of(page.getPageNumber(), 100);

        return questionRepository.findAll(page);
    }

    @Override
    public Optional<Question> findRandomQuestion() {
        long count = questionRepository.count();
        int toPick = new Random().nextInt((int) count);
        Page<Question> questionPage = questionRepository.findAll(PageRequest.of(toPick, 1));
        Question question = null;
        if (questionPage.hasContent()) {
            question = questionPage.getContent().get(0);
        }

        return Optional.ofNullable(question);
    }
}
