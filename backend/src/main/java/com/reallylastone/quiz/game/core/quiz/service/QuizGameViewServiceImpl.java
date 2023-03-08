package com.reallylastone.quiz.game.core.quiz.service;

import com.reallylastone.quiz.exercise.question.mapper.QuestionMapper;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerResponse;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizGameViewServiceImpl implements QuizGameViewService {
    private final QuizGameService quizGameService;
    private final QuestionMapper questionMapper;

    @Override
    public Long startGame(int questions, HttpServletRequest request) {
        return quizGameService.startGame(questions);
    }

    @Override
    public ResponseEntity<QuestionView> next(HttpServletRequest request) {
        return ResponseEntity.ok(questionMapper.mapToView(quizGameService.next()));
    }

    @Override
    public ResponseEntity<QuestionAnswerResponse> answer(QuestionAnswerRequest questionAnswer, HttpServletRequest request) {
        return ResponseEntity.ok(new QuestionAnswerResponse(quizGameService.processAnswer(questionAnswer)));
    }
}
