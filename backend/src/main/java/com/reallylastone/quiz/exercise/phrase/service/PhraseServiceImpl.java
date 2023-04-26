package com.reallylastone.quiz.exercise.phrase.service;

import com.reallylastone.quiz.exercise.phrase.mapper.PhraseMapper;
import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.repository.PhraseRepository;
import com.reallylastone.quiz.exercise.phrase.validation.PhraseValidator;
import com.reallylastone.quiz.util.validation.ValidationErrorsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhraseServiceImpl implements PhraseService {
    private final PhraseRepository phraseRepository;
    private final PhraseValidator phraseValidator;
    private final PhraseMapper phraseMapper;

    @Override
    public Optional<Phrase> findById(Long id) {
        return phraseRepository.findById(id);
    }

    @Override
    public Phrase createPhrase(PhraseCreateRequest request) {
        List<Phrase> phrases = phraseRepository.getByTranslationValues(request.translationMap().values());

        validate(request);

        if (phrases.size() == 1) {
            Phrase toMerge = phrases.get(0);
            toMerge.getTranslationMap().putAll(request.translationMap());

            return toMerge;
        }

        return phraseRepository.save(phraseMapper.mapToEntity(request));

    }

    private void validate(PhraseCreateRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "PhraseCreateRequest");
        phraseValidator.validate(request, errors);

        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
    }
}
