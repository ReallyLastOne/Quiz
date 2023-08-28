package com.reallylastone.quiz.exercise.phrase.service;

import com.reallylastone.quiz.exercise.phrase.model.CSVFileParser;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateBatchResponse;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhraseViewService {
    ResponseEntity<PhraseView> findById(Long id);

    ResponseEntity<PhraseView> createPhrase(PhraseCreateRequest createRequest);

    ResponseEntity<List<PhraseView>> getAllPhrases(int page, int size, String[] languages);

    ResponseEntity<PhraseCreateBatchResponse> createPhrases(MultipartFile file, CSVFileParser parser);
}
