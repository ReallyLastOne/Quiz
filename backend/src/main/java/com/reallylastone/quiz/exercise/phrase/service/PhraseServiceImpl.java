package com.reallylastone.quiz.exercise.phrase.service;

import com.reallylastone.quiz.exercise.phrase.mapper.PhraseMapper;
import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateBatchResponse;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.repository.PhraseRepository;
import com.reallylastone.quiz.exercise.phrase.validation.PhraseValidator;
import com.reallylastone.quiz.user.service.UserService;
import com.reallylastone.quiz.util.validation.ValidationErrorsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhraseServiceImpl implements PhraseService {
    private final PhraseRepository phraseRepository;
    private final PhraseValidator phraseValidator;
    private final PhraseMapper phraseMapper;

    @Override
    public Optional<Phrase> findById(Long id) {
        return phraseRepository.findById(id);
    }

    @Override
    @Transactional
    public Phrase createPhrase(PhraseCreateRequest request) {
        validate(request);
        // TODO: refactor, as it unnecessarily fetches all user/global phrases instead of filtering in on database level
        // TODO: also it would be nice to avoid code duplication as there is same lines in PhraseValidator
        Long ownerId = BooleanUtils.isTrue(request.userPhrase()) ? UserService.getCurrentUser().getId() : null;
        List<Phrase> phrases = phraseRepository.findByOwnerId(ownerId).stream().filter(
                e -> !Collections.disjoint(e.getTranslationMap().entrySet(), request.translationMap().entrySet()))
                .toList();

        if (phrases.size() == 1) {
            Phrase toMerge = phrases.get(0);
            toMerge.getTranslationMap().putAll(request.translationMap());
            log.info("Repeated entries from different phrase. Resulting merged phrase: %s".formatted(toMerge));

            return toMerge;
        }

        return phraseRepository.save(phraseMapper.mapToEntity(request));
    }

    @Override
    public List<Phrase> getAllPhrases(PageRequest page, String[] languages) {
        Long id = UserService.getCurrentUser().getId();

        if (id == null) {
            log.error("Tried to get get all phrases for user but no user present in context");
            throw new IllegalStateException("No authenticated user in the context");
        }

        if (languages == null) {
            return phraseRepository.findByOwnerId(id, page);
        }

        return phraseRepository.findByOwnerId(id, page).stream()
                .filter(e -> new HashSet<>(e.getTranslationMap().keySet().stream().map(Locale::toLanguageTag).toList())
                        .containsAll(List.of(languages)))
                .toList();
    }

    @Override
    @Transactional
    public PhraseCreateBatchResponse createPhrases(List<Phrase> phrases) {
        Map<Long, Map<Locale, String>> correct = new HashMap<>();
        Map<Long, List<String>> incorrect = new HashMap<>();

        for (int i = 0; i < phrases.size(); i++) {
            Phrase phrase = phrases.get(i);
            try {
                Phrase p = createPhrase(new PhraseCreateRequest(phrase.getTranslationMap(), true));
                correct.put((long) i + 1, p.getTranslationMap());
            } catch (ValidationErrorsException e) {
                incorrect.put((long) i + 1, e.getErrors().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
            }
        }

        return new PhraseCreateBatchResponse(correct, incorrect);
    }

    @Override
    public Phrase findRandomPhrase(Locale sourceLanguage, Locale destinationLanguage, Long userId) {
        List<Phrase> phrases = phraseRepository.findByOwnerId(userId).stream()
                .filter(e -> e.getTranslationMap().keySet().containsAll(Set.of(sourceLanguage, destinationLanguage)))
                .toList();
        int toPick = new Random().nextInt(phrases.size());

        return phrases.get(toPick);
    }

    private void validate(PhraseCreateRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "PhraseCreateRequest");
        phraseValidator.validate(request, errors);

        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
    }
}
