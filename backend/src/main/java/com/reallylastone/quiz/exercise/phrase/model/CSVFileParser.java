package com.reallylastone.quiz.exercise.phrase.model;

import com.opencsv.ICSVParser;

import java.util.Optional;

public record CSVFileParser(Character quoteChar, Character separator, Character escapeChar, Boolean strictQuotes,
        Boolean ignoreLeadingWhiteSpace, Boolean ignoreQuotations) {
    public CSVFileParser(Character quoteChar, Character separator, Character escapeChar, Boolean strictQuotes,
            Boolean ignoreLeadingWhiteSpace, Boolean ignoreQuotations) {
        this.quoteChar = Optional.ofNullable(quoteChar).orElse(ICSVParser.DEFAULT_QUOTE_CHARACTER);
        this.separator = Optional.ofNullable(separator).orElse(ICSVParser.DEFAULT_SEPARATOR);
        this.escapeChar = Optional.ofNullable(escapeChar).orElse(ICSVParser.DEFAULT_ESCAPE_CHARACTER);
        this.strictQuotes = Optional.ofNullable(strictQuotes).orElse(false);
        this.ignoreLeadingWhiteSpace = Optional.ofNullable(ignoreLeadingWhiteSpace).orElse(true);
        this.ignoreQuotations = Optional.ofNullable(ignoreQuotations).orElse(false);
    }

    public static CSVFileParser fromSeparator(Character separator) {
        return new CSVFileParser(null, separator, null, null, null, null);
    }
}
