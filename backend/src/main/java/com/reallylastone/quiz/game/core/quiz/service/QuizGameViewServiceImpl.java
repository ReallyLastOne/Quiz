package com.reallylastone.quiz.game.core.quiz.service;

import com.reallylastone.quiz.exercise.question.mapper.QuestionMapper;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerResponse;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import com.reallylastone.quiz.game.core.quiz.model.ActiveQuizGameSessionView;
import com.reallylastone.quiz.util.validation.GenericResponse;
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
    public ResponseEntity<GenericResponse> startGame(int questions, HttpServletRequest request) {
        quizGameService.startGame(questions);
        return ResponseEntity.ok(new GenericResponse("Successfully started a quiz game"));
    }

    @Override
    public ResponseEntity<QuestionView> next(HttpServletRequest request) {
        return ResponseEntity.ok(questionMapper.mapToView(quizGameService.next()));
    }

    @Override
    public ResponseEntity<QuestionAnswerResponse> answer(QuestionAnswerRequest questionAnswer, HttpServletRequest request) {
        return ResponseEntity.ok(new QuestionAnswerResponse(quizGameService.processAnswer(questionAnswer)));
    }

    @Override
    public ResponseEntity<GenericResponse> stopGame() {
        quizGameService.stopGame();
        return ResponseEntity.ok(new GenericResponse("Successfully stopped quiz game"));
    }

    @Override
    public ResponseEntity<ActiveQuizGameSessionView> findActive() {
        return quizGameService.findActive()
                .map(session -> new ActiveQuizGameSessionView(session, questionMapper::mapToView))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("User has no active session"));
    }
}
