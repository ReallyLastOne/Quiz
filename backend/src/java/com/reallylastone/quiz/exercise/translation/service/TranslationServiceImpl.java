package com.reallylastone.quiz.exercise.translation.service;

import com.reallylastone.quiz.exercise.translation.model.Translation;
import com.reallylastone.quiz.exercise.translation.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {
    private final TranslationRepository translationRepository;

    @Override
    public Optional<Translation> findById(Long id) {
        return translationRepository.findById(id);
    }

    @Override
    public void createTranslation(Translation translation) {
        translationRepository.save(translation);
    }
}
