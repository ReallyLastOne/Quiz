package com.reallylastone.quiz.game.core.quiz.service;

import com.reallylastone.quiz.exercise.core.ExerciseState;
import com.reallylastone.quiz.exercise.question.mapper.QuestionMapper;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionAnswerResponse;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import com.reallylastone.quiz.game.core.quiz.model.ActiveQuizGameSessionView;
import com.reallylastone.quiz.game.core.quiz.model.DoneQuizSessionView;
import com.reallylastone.quiz.game.core.quiz.model.ListOfPlayedGamesView;
import com.reallylastone.quiz.game.core.quiz.model.QuizGameSession;
import com.reallylastone.quiz.util.GenericResponse;
import com.reallylastone.quiz.util.Messages;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizGameViewServiceImpl implements QuizGameViewService {
    private final QuizGameService quizGameService;
    private final QuestionMapper questionMapper;
    private final Messages messages;

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
    public ResponseEntity<QuestionAnswerResponse> answer(QuestionAnswerRequest questionAnswer,
            HttpServletRequest request) {
        // if no active session, then processAnswer method will throw exception, so after there is guarantee that
        // 'current' is present
        Optional<QuizGameSession> current = quizGameService.findActive();

        boolean correctAnswer = quizGameService.processAnswer(questionAnswer);

        int questionsLeft = current.map(c -> c.getQuestionSize() - c.getQuestionsAndStatus().size()).orElse(0);

        return ResponseEntity.ok(
                new QuestionAnswerResponse(correctAnswer, questionsLeft, current.get().countOf(ExerciseState.CORRECT)));
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
                .orElseThrow(() -> new IllegalArgumentException(messages.getMessage("user.session.inactive", null)));
    }

    @Override
    public ResponseEntity<ListOfPlayedGamesView> findRecent(int games) {
        return ResponseEntity.ok(new ListOfPlayedGamesView(quizGameService.findRecent(games).stream()
                .map(session -> new DoneQuizSessionView((int) session.countOf(ExerciseState.CORRECT),
                        session.getFinishDate(), session.getQuestionSize()))
                .toList()));
    }
}
