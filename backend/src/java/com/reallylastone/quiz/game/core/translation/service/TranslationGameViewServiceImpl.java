package com.reallylastone.quiz.game.core.translation.service;

import com.reallylastone.quiz.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TranslationGameViewServiceImpl implements TranslationGameViewService {
    private final TranslationGameService translationGameService;

    @Override
    public Long startGame(Locale sourceLanguage, Locale destinationLanguage, HttpRequest request) {
        return translationGameService.startGame(sourceLanguage, destinationLanguage,
                (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
