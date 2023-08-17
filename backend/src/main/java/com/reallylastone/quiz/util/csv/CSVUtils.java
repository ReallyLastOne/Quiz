package com.reallylastone.quiz.util.csv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.reallylastone.quiz.exercise.phrase.model.CSVFileParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CSVUtils {
    public static final CSVParserBuilder BASE_BUILDER = new CSVParserBuilder();
    public static final List<Character> COMMON_DELIMITERS = List.of(',', ';', '\t', ' ', '|', ':');

    /**
     * Creates a CSVReader from a MultipartFile and provided CSVFileParser. If provided parser is null, then separator is determined and remaining properties are defaults.
     *
     * @param multipartFile the MultipartFile to create the CSVReader from
     * @param parser        parser to build reader from
     * @return a CSVReader that reads from the given MultipartFile
     * @throws IOException if an I/O error occurs while reading from the MultipartFile
     */
    public static CSVReader toCSVReader(MultipartFile multipartFile, CSVFileParser parser) throws IOException {
        byte[] bytes = multipartFile.getBytes();

        if (parser == null) {
            parser = CSVFileParser.fromSeparator(determineSeparator(grabFirstLine(bytes)));
        }

        return new CSVReaderBuilder(toFileReader(bytes, multipartFile.getName())).withCSVParser(toCSVParser(parser)).build();
    }

    /**
     * Extracts the first line of text from a byte array.
     * If no end of line byte is provided then String built from all bytes is returned.
     *
     * @param bytes bytes to be interpreted as in UTF-8 encoding
     * @return first line of a text
     */
    public static String grabFirstLine(byte[] bytes) {
        StringBuilder firstLine = new StringBuilder();

        for (byte b : bytes) {
            if (b == '\n' || b == '\r') {
                break;
            }
            firstLine.append((char) b);
        }

        return firstLine.toString();
    }

    /**
     * Determines the most common separator character in a given string.
     *
     * @param firstLine the string to analyze
     * @return the most common separator character in the string
     */
    public static char determineSeparator(String firstLine) {
        char maxDelimiter = COMMON_DELIMITERS.get(0);
        long maxCount = 0;

        for (char delimiter : COMMON_DELIMITERS) {
            long count = StringUtils.countMatches(firstLine, delimiter);

            if (count > maxCount) {
                maxDelimiter = delimiter;
                maxCount = count;
            }
        }

        return maxDelimiter;
    }

    /**
     * Creates a FileReader from a byte array and a file name.
     *
     * @param bytes the byte array to create the FileReader from
     * @param name  the name of the file to create
     * @return a FileReader that reads from the given byte array
     * @throws IOException if an I/O error occurs while creating the file or writing to it
     */
    public static FileReader toFileReader(byte[] bytes, String name) throws IOException {
        File file = new File(name);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        }

        return new FileReader(file);
    }


    /**
     * Creates a CSVParser from a CSVFileParser.
     *
     * @param parser the CSVFileParser to create the CSVParser from
     * @return a CSVParser that is configured according to the given CSVFileParser
     */
    private static CSVParser toCSVParser(CSVFileParser parser) {
        return BASE_BUILDER.withSeparator(parser.separator()).withQuoteChar(parser.quoteChar()).withIgnoreLeadingWhiteSpace(parser.ignoreLeadingWhiteSpace()).withEscapeChar(parser.escapeChar()).withIgnoreQuotations(parser.ignoreQuotations()).withStrictQuotes(parser.strictQuotes()).build();
    }
}
