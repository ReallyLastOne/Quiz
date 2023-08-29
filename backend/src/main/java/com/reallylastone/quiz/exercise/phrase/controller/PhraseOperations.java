package com.reallylastone.quiz.exercise.phrase.controller;

import com.reallylastone.quiz.exercise.phrase.model.CSVFileParser;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateBatchResponse;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCreateRequest;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(description = "Provides operations to work on Phrase objects", name = "Phrase Controller")
@RequestMapping(value = "/api/v1/exercises/phrases", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PhraseOperations {

    @Operation(summary = "Creates or merges if possible a phrase translations. Phrase is created for user or global context")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "422", description = "If content is wrong or if there are translations that cannot be merge into another phrase", content = @Content)})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PhraseView> createPhrase(@RequestBody PhraseCreateRequest createRequest);

    @Operation(summary = """
            Creates or merges all provided phrases in CSV file. CSV file must be encoded in UTF-8 format.
            If parser is provided, then file is parsed using all non null parsers' properties. For null properties defaults are used:
            quoteChar: "
            separator: ,
            escapeChar: \\
            strictQuotes: false
            ignoreLeadingWhiteSpace: true
            ignoreQuotations: false
            For null parser separator is determined on the fly and remaining properties are defaults.
            Phrases can be created only for user.
            """)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "422", description = "If content is wrong or if there are translations that cannot be merge into another phrase", content = @Content)})
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<PhraseCreateBatchResponse> createPhrases(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "body", required = false) CSVFileParser parser) throws IOException;

    @Operation(summary = "Get all phrases for currently authenticated user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK", content = @Content)})
    @GetMapping
    ResponseEntity<List<PhraseView>> getAllPhrases(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "100") int size,
                                                   @RequestParam(value = "languages", required = false) String[] languages);
}
