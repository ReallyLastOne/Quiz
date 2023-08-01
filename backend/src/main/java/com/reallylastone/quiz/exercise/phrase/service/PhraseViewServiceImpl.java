package com.reallylastone.quiz.exercise.phrase.service;

import com.reallylastone.quiz.exercise.phrase.mapper.PhraseMapper;
import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseFilter;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhraseViewServiceImpl implements PhraseViewService {
    private final PhraseService phraseService;
    private final PhraseMapper phraseMapper;

    @Override
    public ResponseEntity<PhraseView> findById(Long id) {
        Optional<Phrase> exerciseOptional = phraseService.findById(id);
        return exerciseOptional.map(exercise -> ResponseEntity.ok(phraseMapper.mapToView(exercise))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<PhraseView> createPhrase(PhraseCreateRequest createRequest) {
        return ResponseEntity.ok(phraseMapper.mapToView(phraseService.createPhrase(createRequest)));
    }

    @Override
    public ResponseEntity<List<PhraseView>> getAllPhrases(int page, int size, PhraseFilter phraseFilter) {
        return ResponseEntity.ok(
                phraseService.getAllPhrases(PageRequest.of(page, size), phraseFilter).stream().map(phraseMapper::mapToView)
                        .toList());
    }
}
