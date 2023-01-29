package com.reallylastone.quiz.exercise.translation.service;

import com.reallylastone.quiz.exercise.translation.model.Translation;
import com.reallylastone.quiz.exercise.translation.repository.TranslationRepository;
import com.reallylastone.quiz.exercise.translation.validation.TranslationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {
    private final TranslationRepository translationRepository;
    private final TranslationValidator translationValidator;

    @Override
    public Optional<Translation> findById(Long id) {
        return translationRepository.findById(id);
    }

    @Override
    @Transactional
    public Translation createTranslation(Translation translation) {
        List<Translation> translations = translationRepository.getByTranslationValues(translation.getTranslationMap().values());

        translationValidator.validate(translation, translations);

        if (translations.isEmpty()) {
            translationRepository.save(translation);
        }

        if (translations.size() == 1) {
            Translation toMerge = translations.get(0);
            toMerge.getTranslationMap().putAll(translation.getTranslationMap());
        }

        return null;
    }
}
