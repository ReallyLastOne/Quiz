package com.reallylastone.quiz.game.translation.service;

import com.reallylastone.quiz.exercise.ExerciseState;
import com.reallylastone.quiz.exercise.phrase.mapper.PhraseMapper;
import com.reallylastone.quiz.exercise.phrase.model.PhraseToTranslate;
import com.reallylastone.quiz.game.translation.model.ActiveTranslationGameSessionView;
import com.reallylastone.quiz.game.translation.model.DoneTranslationSessionView;
import com.reallylastone.quiz.game.translation.model.HighscoreTranslationEntry;
import com.reallylastone.quiz.game.translation.model.ListOfPlayedGamesView;
import com.reallylastone.quiz.game.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.translation.model.PhraseAnswerResponse;
import com.reallylastone.quiz.game.translation.model.TranslationGameSession;
import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.util.GenericResponse;
import com.reallylastone.quiz.util.Messages;
import com.reallylastone.quiz.util.validation.StateValidationErrorsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.reallylastone.quiz.util.validation.StateValidationError.USER_INACTIVE_SESSION;

@Service
@RequiredArgsConstructor
public class TranslationGameViewServiceImpl implements TranslationGameViewService {
    private final TranslationGameService translationGameService;
    private final PhraseMapper phraseMapper;
    private final Messages messages;

    @Override
    public Long startGame(Locale sourceLanguage, Locale destinationLanguage, int phrases, HttpServletRequest request) {
        return translationGameService.startGame(sourceLanguage, destinationLanguage, phrases,
                (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public ResponseEntity<PhraseToTranslate> next() {
        return ResponseEntity.ok(translationGameService.next());
    }

    @Override
    public ResponseEntity<PhraseAnswerResponse> answer(PhraseAnswerRequest phraseAnswer) {
        Optional<TranslationGameSession> current = translationGameService.findActive();

        boolean correctAnswer = translationGameService.processAnswer(phraseAnswer);

        int phrasesLeft = current.map(c -> c.getPhrasesSize() - c.getTranslationsAndStatus().size()).orElse(0);

        return ResponseEntity
                .ok(new PhraseAnswerResponse(correctAnswer, phrasesLeft, current.get().countOf(ExerciseState.CORRECT)));
    }

    @Override
    public ResponseEntity<GenericResponse> stopGame() {
        translationGameService.stopGame();
        return ResponseEntity.ok(new GenericResponse("Successfully stopped phrase game"));
    }

    @Override
    public ResponseEntity<ActiveTranslationGameSessionView> findActive() {
        return translationGameService.findActive()
                .map(session -> new ActiveTranslationGameSessionView(session, phraseMapper::mapToView))
                .map(ResponseEntity::ok).orElseThrow(
                        () -> new StateValidationErrorsException(Collections.singletonList(USER_INACTIVE_SESSION)));
    }

    @Override
    public ResponseEntity<ListOfPlayedGamesView> findRecent(int games) {
        return ResponseEntity.ok(new ListOfPlayedGamesView(translationGameService.findRecent(games).stream()
                .map(session -> new DoneTranslationSessionView((int) session.countOf(ExerciseState.CORRECT),
                        session.getFinishDate(), session.getPhrasesSize()))
                .toList()));
    }

    @Override
    public ResponseEntity<List<HighscoreTranslationEntry>> getHighscore() {
        return ResponseEntity.ok(translationGameService.getHighsore());
    }
}
