package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.controller.QuestionAddResponse;
import com.reallylastone.quiz.exercise.question.mapper.QuestionMapper;
import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionCreateRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionViewServiceImpl implements QuestionViewService {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @Override
    public ResponseEntity<QuestionView> findById(Long id) {
        Optional<Question> exerciseOptional = questionService.findById(id);

        return exerciseOptional.map(exercise -> ResponseEntity.ok(questionMapper.mapToView(exercise))).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<QuestionView>> findAll(Pageable page) {
        Page<Question> all = questionService.findAll(page);

        return ResponseEntity.ok(all.stream().map(questionMapper::mapToView).toList());
    }

    @Override
    public ResponseEntity<QuestionView> findRandomQuestion() {
        Question question = questionService.findRandomQuestion();

        return ResponseEntity.ok(questionMapper.mapToView(question));
    }

    @Override
    public ResponseEntity<QuestionAddResponse> create(QuestionCreateRequest request) {
        Question question = questionService.create(questionMapper.mapToEntity(request));

        return ResponseEntity.ok(questionMapper.mapToResponse(question));
    }
}
