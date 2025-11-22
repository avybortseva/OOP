package ru.nsu.g.a.vybortseva.substring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


class SubstringSearcherTest {

    @TempDir
    Path tempDir;

    @Test
    void testBasicSubstringSearch() throws IOException {
        Path testFile = tempDir.resolve("test1.txt");
        Files.writeString(testFile, "hello world");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "world");
        assertEquals(List.of(6L), result);
    }

    @Test
    void testMultipleOccurrences() throws IOException {
        Path testFile = tempDir.resolve("test2.txt");
        Files.writeString(testFile, "abracadabra");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "bra");
        assertEquals(List.of(1L, 8L), result);
    }

    @Test
    void testOverlappingOccurrences() throws IOException {
        Path testFile = tempDir.resolve("test3.txt");
        Files.writeString(testFile, "aaaaaa");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "aaa");
        assertEquals(List.of(0L, 1L, 2L, 3L), result);
    }

    @Test
    void testSubstringAtBeginning() throws IOException {
        Path testFile = tempDir.resolve("test4.txt");
        Files.writeString(testFile, "start of the text");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "start");
        assertEquals(List.of(0L), result);
    }

    @Test
    void testSubstringAtEnd() throws IOException {
        // Create test file
        Path testFile = tempDir.resolve("test5.txt");
        Files.writeString(testFile, "this is the end");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "end");
        assertEquals(List.of(12L), result);
    }

    @Test
    void testSubstringNotFound() throws IOException {
        Path testFile = tempDir.resolve("test6.txt");
        Files.writeString(testFile, "some text content");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "xyz");
        assertTrue(result.isEmpty());
    }

    @Test
    void testEmptySubstring() throws IOException {
        Path testFile = tempDir.resolve("test7.txt");
        Files.writeString(testFile, "some text");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "");
        assertTrue(result.isEmpty());
    }

    @Test
    void testSubstringLongerThanFile() throws IOException {
        Path testFile = tempDir.resolve("test8.txt");
        Files.writeString(testFile, "short");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "very long substring");
        assertTrue(result.isEmpty());
    }

    @Test
    void testExactMatch() throws IOException {
        Path testFile = tempDir.resolve("test9.txt");
        String content = "exact content";
        Files.writeString(testFile, content);

        List<Long> result = SubstringSearcher.find(testFile.toString(), content);
        assertEquals(List.of(0L), result);
    }

    @Test
    void testMultipleUtf8Occurrences() throws IOException {
        // Create test file
        Path testFile = tempDir.resolve("test11.txt");
        Files.writeString(testFile, "абракадабра");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "бра");
        assertEquals(List.of(1L, 8L), result);
    }

    @Test
    void testCaseSensitivity() throws IOException {
        Path testFile = tempDir.resolve("test12.txt");
        Files.writeString(testFile, "Hello HELLO hello");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "Hello");
        assertEquals(List.of(0L), result);
    }

    @Test
    void testSpecialCharacters() throws IOException {
        Path testFile = tempDir.resolve("test13.txt");
        Files.writeString(testFile, "line1\nline2\tline3\r\nline4");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "line");
        assertEquals(List.of(0L, 6L, 12L, 19L), result);
    }

    @Test
    void testEmptyFile() throws IOException {
        Path testFile = tempDir.resolve("test14.txt");
        Files.writeString(testFile, "");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "text");
        assertTrue(result.isEmpty());
    }

    @Test
    void testSingleCharacter() throws IOException {
        Path testFile = tempDir.resolve("test15.txt");
        Files.writeString(testFile, "a");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "a");
        assertEquals(List.of(0L), result);
    }

    @Test
    void testRepeatingPattern() throws IOException {
        Path testFile = tempDir.resolve("test16.txt");
        Files.writeString(testFile, "abcabcabc");

        List<Long> result = SubstringSearcher.find(testFile.toString(), "abc");
        assertEquals(List.of(0L, 3L, 6L), result);
    }

    @Test
    void testFileNotFound() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            SubstringSearcher.find("nonexistent_file.txt", "test");
        });
        assertTrue(exception.getMessage().contains("Error reading file"));
    }

    @Test
    void testLongSubstring() throws IOException {
        Path testFile = tempDir.resolve("test17.txt");
        String longText = "This is a relatively long text content that should contain "
                + "the pattern we are looking for in this specific test case";
        Files.writeString(testFile, longText);

        List<Long> result = SubstringSearcher.find(testFile.toString(), "relatively long text");
        assertEquals(List.of(10L), result);
    }

    @Test
    void testNonAsciiCharacters() throws IOException {
        Path tempFile = tempDir.resolve("unicode-test.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8)) {
            writer.write("Привет мир! Hello world!");
        }

        List<Long> result = SubstringSearcher.find(tempFile.toString(), "мир");
        assertEquals(List.of(7L), result);
    }

    @Test
    void testLargeFile() throws IOException {
        Path largeFile = tempDir.resolve("largefile.txt");

        long targetSize = 17L * 1024 * 1024 * 1024;
        String pattern = "TEST_PATTERN";
        String fillerLine = "x".repeat(1000) + "\n";

        try (BufferedWriter writer = Files.newBufferedWriter(largeFile, StandardCharsets.UTF_8)) {
            long bytesWritten = 0;
            int lineCount = 0;

            while (bytesWritten < targetSize) {
                if (lineCount % 10000 == 0) {
                    writer.write(pattern + "\n");
                    bytesWritten += pattern.length() + 1;
                } else {
                    writer.write(fillerLine);
                    bytesWritten += fillerLine.length();
                }
                lineCount++;

                if (lineCount % 100000 == 0) {
                    System.out.printf("Written: %.2f GB%n", (double)bytesWritten / (1024*1024*1024));
                }
            }
        }

        List<Long> result = SubstringSearcher.find(largeFile.toString(), "TEST_PATTERN");

        System.out.println("Found occurrences: " + result.size());
        assertEquals(true, result.size() > 0);
    }
}