package com.reallylastone.quiz.game.core.translation.service;

import com.reallylastone.quiz.exercise.phrase.mapper.PhraseMapper;
import com.reallylastone.quiz.exercise.phrase.model.PhraseToTranslate;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerRequest;
import com.reallylastone.quiz.game.core.translation.model.PhraseAnswerResponse;
import com.reallylastone.quiz.user.model.UserEntity;
import com.reallylastone.quiz.util.validation.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

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
        return ResponseEntity.ok(new PhraseAnswerResponse(translationGameService.processAnswer(phraseAnswer)));
    }

    @Override
    public ResponseEntity<GenericResponse> stopGame() {
        translationGameService.stopGame();
        return ResponseEntity.ok(new GenericResponse("Successfully stopped phrase game"));
    }
}
