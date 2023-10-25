package com.reallylastone.quiz.exercise.phrase.validation;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.repository.PhraseRepository;
import com.reallylastone.quiz.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.Collections;
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

        // TODO: refactor, as it unnecessarily fetches all user/global phrases instead of filtering in on database level
        // TODO: also it would be nice to avoid code duplication as there is same lines in PhraseValidator
        Long ownerId = BooleanUtils.isTrue(request.userPhrase()) ? UserService.getCurrentUser().getId() : null;
        List<Phrase> phrases = phraseRepository.findByOwnerId(ownerId).stream().filter(
                e -> !Collections.disjoint(e.getTranslationMap().entrySet(), request.translationMap().entrySet()))
                .toList();

        if (validatorAdapter != null) {
            validatorAdapter.validate(request, errors);
        }

        if (phrases.size() >= 2)
            errors.rejectValue("translationMap", "invalid.translationMap",
                    "translationMap must have unique entries, repeated entries from Phrases of id: "
                            + phrases.stream().map(Phrase::getId).collect(Collectors.toSet()));
    }
}
