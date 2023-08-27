package com.reallylastone.quiz.unit.exercise;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.repository.QuestionRepository;
import com.reallylastone.quiz.exercise.question.service.QuestionService;
import com.reallylastone.quiz.exercise.question.service.QuestionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuestionServiceTest {
    private QuestionService questionService;
    private QuestionRepository questionRepository;

    @BeforeEach
    public void setUp() {
        questionRepository = mock(QuestionRepository.class);

        questionService = new QuestionServiceImpl(
                questionRepository
        );
    }

    @Test
    void shouldDrawRandomQuestion() {
        Question question = new Question();
        when(questionRepository.count()).thenReturn(25L);
        when(questionRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(question)));

        Assertions.assertEquals(question, questionService.findRandomQuestion());
    }

}
