package ru.nsu.g.a.vybortseva.substring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubstringSearcherTest {

    @TempDir
    Path tempDir;

    @Test
    void testBasicSubstringSearch() throws IOException {
        Path testFile = tempDir.resolve("test1.txt");
        Files.writeString(testFile, "hello world");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "world");
        assertEquals(List.of(6), result);
    }

    @Test
    void testMultipleOccurrences() throws IOException {
        Path testFile = tempDir.resolve("test2.txt");
        Files.writeString(testFile, "abracadabra");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "bra");
        assertEquals(List.of(1, 8), result);
    }

    @Test
    void testOverlappingOccurrences() throws IOException {
        Path testFile = tempDir.resolve("test3.txt");
        Files.writeString(testFile, "aaaaaa");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "aaa");
        assertEquals(List.of(0, 1, 2, 3), result);
    }

    @Test
    void testSubstringAtBeginning() throws IOException {
        Path testFile = tempDir.resolve("test4.txt");
        Files.writeString(testFile, "start of the text");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "start");
        assertEquals(List.of(0), result);
    }

    @Test
    void testSubstringAtEnd() throws IOException {
        // Create test file
        Path testFile = tempDir.resolve("test5.txt");
        Files.writeString(testFile, "this is the end");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "end");
        assertEquals(List.of(12), result);
    }

    @Test
    void testSubstringNotFound() throws IOException {
        Path testFile = tempDir.resolve("test6.txt");
        Files.writeString(testFile, "some text content");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "xyz");
        assertTrue(result.isEmpty());
    }

    @Test
    void testEmptySubstring() throws IOException {
        Path testFile = tempDir.resolve("test7.txt");
        Files.writeString(testFile, "some text");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "");
        assertTrue(result.isEmpty());
    }

    @Test
    void testSubstringLongerThanFile() throws IOException {
        Path testFile = tempDir.resolve("test8.txt");
        Files.writeString(testFile, "short");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "very long substring");
        assertTrue(result.isEmpty());
    }

    @Test
    void testExactMatch() throws IOException {
        Path testFile = tempDir.resolve("test9.txt");
        String content = "exact content";
        Files.writeString(testFile, content);

        List<Integer> result = SubstringSearcher.find(testFile.toString(), content);
        assertEquals(List.of(0), result);
    }

    @Test
    void testMultipleUtf8Occurrences() throws IOException {
        // Create test file
        Path testFile = tempDir.resolve("test11.txt");
        Files.writeString(testFile, "абракадабра");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "бра");
        assertEquals(List.of(1, 8), result);
    }

    @Test
    void testCaseSensitivity() throws IOException {
        Path testFile = tempDir.resolve("test12.txt");
        Files.writeString(testFile, "Hello HELLO hello");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "Hello");
        assertEquals(List.of(0), result);
    }

    @Test
    void testSpecialCharacters() throws IOException {
        Path testFile = tempDir.resolve("test13.txt");
        Files.writeString(testFile, "line1\nline2\tline3\r\nline4");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "line");
        assertEquals(List.of(0, 6, 12, 19), result);
    }

    @Test
    void testEmptyFile() throws IOException {
        Path testFile = tempDir.resolve("test14.txt");
        Files.writeString(testFile, "");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "text");
        assertTrue(result.isEmpty());
    }

    @Test
    void testSingleCharacter() throws IOException {
        Path testFile = tempDir.resolve("test15.txt");
        Files.writeString(testFile, "a");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "a");
        assertEquals(List.of(0), result);
    }

    @Test
    void testRepeatingPattern() throws IOException {
        Path testFile = tempDir.resolve("test16.txt");
        Files.writeString(testFile, "abcabcabc");

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "abc");
        assertEquals(List.of(0, 3, 6), result);
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

        List<Integer> result = SubstringSearcher.find(testFile.toString(), "relatively long text");
        assertEquals(List.of(10), result);
    }
}