package com.reallylastone.quiz.game.core.translation.service;

import com.reallylastone.quiz.exercise.core.ExerciseState;
import com.reallylastone.quiz.exercise.phrase.mapper.PhraseMapper;
import com.reallylastone.quiz.exercise.phrase.model.PhraseToTranslate;
import com.reallylastone.quiz.game.core.translation.model.ActiveTranslationGameSessionView;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerResponse;
import com.reallylastone.quiz.game.core.translation.model.TranslationGameSession;
import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.util.validation.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TranslationGameViewServiceImpl implements TranslationGameViewService {
    private final TranslationGameService translationGameService;
    private final PhraseMapper phraseMapper;

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

        return ResponseEntity.ok(new PhraseAnswerResponse(correctAnswer, phrasesLeft,
                current.get().countOf(ExerciseState.CORRECT)));
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
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalStateException("User has no active session"));
    }
}
