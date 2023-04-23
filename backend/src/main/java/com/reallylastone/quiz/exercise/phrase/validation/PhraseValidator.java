package com.reallylastone.quiz.exercise.phrase.validation;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.repository.PhraseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhraseValidator implements Validator {
    private final PhraseRepository phraseRepository;
    private final SpringValidatorAdapter validatorAdapter;

    @Override
    public boolean supports(Class<?> clazz) {
        return PhraseCreateRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PhraseCreateRequest request = (PhraseCreateRequest) target;

        List<Phrase> phrases = phraseRepository.getByTranslationValues(request.translationMap().values());

        if (validatorAdapter != null) {
            validatorAdapter.validate(request, errors);
        }

        if (phrases.size() >= 2)
            errors.rejectValue("translationMap", "invalid.translationMap",
                    "translationMap must have unique entries, repeated entries from Phrases: " +
                            phrases.stream().map(Phrase::getId).collect(Collectors.toSet()));
    }
}
