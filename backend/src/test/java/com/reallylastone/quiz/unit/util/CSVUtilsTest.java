package com.reallylastone.quiz.unit.util;

import com.opencsv.CSVReader;
import com.reallylastone.quiz.util.csv.CSVUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.Reader;
import java.util.stream.Stream;

class CSVUtilsTest {
    @Parameterized.Parameters
    static Stream<String> firstLineData() {
        return Stream.of(
                "firstLine",
                "firstLine\rasfd\n",
                "firstLine\nasfd\n",
                "firstLine\r\nasfd\n",
                "firstLine\n\rasfd\n",
                "firstLine\n\nasfd\n"
        );
    }

    @Parameterized.Parameters
    static Stream<Arguments> detectSeparatorData() {
        return Stream.of(
                Arguments.of((Object)
                        new String[]{
                                "pl;en;it", ";"}),
                Arguments.of((Object)
                        new String[]{"pl;en,it;es", ";"}),
                Arguments.of((Object)
                        new String[]{";;;", ";"}),
                Arguments.of((Object)
                        new String[]{"\"pl\";\"en\";\"it\";es", ";"}),
                Arguments.of((Object)
                        new String[]{"\tpl\ten\t", "\t"})
        );
    }

    @ParameterizedTest
    @MethodSource(value = "firstLineData")
    void shouldGrabFirstLine(String data) {
        Assertions.assertEquals("firstLine", CSVUtils.grabFirstLine(data.getBytes()));
    }

    @ParameterizedTest
    @MethodSource(value = "detectSeparatorData")
    void shouldDetectSeparator(String[] data) {
        Assertions.assertEquals(data[1].toCharArray()[0], CSVUtils.determineSeparator(data[0]));
    }

    @Test
    void shouldCreateCorrectCSVReader() throws Exception {
        try (CSVReader reader = CSVUtils.toCSVReader(new MockMultipartFile("ehh", "bytes".getBytes()), null)) {
            Assertions.assertEquals(",".toCharArray()[0], reader.getParser().getSeparator());
        }
    }

    @Test
    void shouldReadCorrectNumberOfCharacter() throws IOException {
        try (Reader fileReader = CSVUtils.toReader("bytes".getBytes())) {
            char[] buffer = new char[5];
            int read = fileReader.read(buffer, 0, 5);
            Assertions.assertEquals(5, read);
        }
    }
}
