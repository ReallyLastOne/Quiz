package com.reallylastone.quiz.exercise.phrase.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.reallylastone.quiz.exercise.phrase.mapper.PhraseMapper;
import com.reallylastone.quiz.exercise.phrase.model.CSVFileParser;
import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCSVEntry;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateBatchResponse;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import com.reallylastone.quiz.util.csv.CSVUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<PhraseCreateBatchResponse> createPhrases(MultipartFile multipartFile, CSVFileParser parser) {
        CsvToBean<PhraseCSVEntry> entries = null;
        try {
            entries = new CsvToBeanBuilder<PhraseCSVEntry>(CSVUtils.toCSVReader(multipartFile, parser))
                    .withMappingStrategy(new PhraseCSVEntryMappingStrategy())
                    .withType(PhraseCSVEntry.class)
                    .build();
        } catch (IOException e) {
            throw new IllegalStateException("Could not access file " + multipartFile.getName(), e);
        }

        List<PhraseCSVEntry> phrases = IteratorUtils.toList(entries.iterator());

        return ResponseEntity.ok(phraseService.createPhrases(phraseMapper.mapToEntities(phrases)));
    }

    @Override
    public ResponseEntity<List<PhraseView>> getAllPhrases(int page, int size, String[] languages) {
        return ResponseEntity.ok(
                phraseService.getAllPhrases(PageRequest.of(page, size), languages).stream().map(phraseMapper::mapToView)
                        .toList());
    }
}
