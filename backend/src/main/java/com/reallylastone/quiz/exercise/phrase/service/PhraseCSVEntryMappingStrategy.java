package com.reallylastone.quiz.exercise.phrase.service;

import com.opencsv.CSVReader;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import com.reallylastone.quiz.exercise.phrase.model.PhraseCSVEntry;

import java.io.IOException;
import java.util.Arrays;

public class PhraseCSVEntryMappingStrategy implements MappingStrategy<PhraseCSVEntry> {
    private String[] headers = new String[]{};
    private Class<? extends PhraseCSVEntry> type;

    @Override
    public void captureHeader(CSVReader reader) throws IOException, CsvRequiredFieldEmptyException {
        try {
            headers = reader.readNext();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] generateHeader(PhraseCSVEntry bean) throws CsvRequiredFieldEmptyException {
        return headers;
    }

    @Override
    public PhraseCSVEntry populateNewBean(String[] line) throws CsvBeanIntrospectionException, CsvFieldAssignmentException, CsvChainedException {
        return new PhraseCSVEntry(Arrays.asList(headers), Arrays.stream(line).map(String::trim).toList());
    }

    @Override
    public void setType(Class<? extends PhraseCSVEntry> type) throws CsvBadConverterException {
        this.type = type;
    }

    @Override
    public String[] transmuteBean(PhraseCSVEntry bean) throws CsvFieldAssignmentException, CsvChainedException {
        return bean.words().toArray(new String[]{});
    }
}
