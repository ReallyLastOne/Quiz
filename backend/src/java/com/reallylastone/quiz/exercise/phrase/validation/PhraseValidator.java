package com.reallylastone.quiz.exercise.phrase.validation;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.repository.PhraseRepository;
import com.reallylastone.quiz.util.ValidationUtils;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhraseValidator {
    private final PhraseRepository phraseRepository;

    public void validate(Phrase phrase, List<Phrase> phrases) {
        if (phrases.size() >= 2)
            throw new ConstraintViolationException(ValidationUtils.createConstraintViolationSet("translationMap",
                    "translationMap must have unique entries " + phrase.getId()));
    }
}
