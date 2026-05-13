package ru.nsu.g.a.vybortseva.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.g.a.vybortseva.model.TestResult;

class TestReportParserTest {

    @TempDir
    Path tempDir;

    private TestReportParser parser;
    private File taskDir;

    @BeforeEach
    void setUp() throws IOException {
        parser = new TestReportParser();
        taskDir = tempDir.toFile();
    }

    @Test
    void testParseWithValidXmlReport() throws IOException {
        Path reportsDir = taskDir.toPath().resolve("build/test-results/test");
        Files.createDirectories(reportsDir);

        File xmlFile = reportsDir.resolve("TEST-Example.xml").toFile();
        String xmlContent = """
                <?xml version="1.0" encoding="UTF-8"?>
                <testsuite name="ExampleTest" tests="10" failures="2" errors="1" skipped="1">
                </testsuite>
                """;
        try (FileWriter writer = new FileWriter(xmlFile)) {
            writer.write(xmlContent);
        }

        TestResult result = parser.parse(taskDir, "test-task");

        assertEquals("test-task", result.getTaskId());
        assertTrue(result.isBuildSuccess());
        assertEquals("OK", result.getStatus());
        assertEquals(6, result.getTestPassed());
        assertEquals(10, result.getTestsTotal());
    }

    @Test
    void testParseWithMultipleXmlReports() throws IOException {
        Path reportsDir = taskDir.toPath().resolve("build/test-results/test");
        Files.createDirectories(reportsDir);

        File xmlFile1 = reportsDir.resolve("TEST-Test1.xml").toFile();
        String xmlContent1 = """
                <?xml version="1.0" encoding="UTF-8"?>
                <testsuite name="Test1" tests="5" failures="1" errors="0" skipped="0">
                </testsuite>
                """;
        try (FileWriter writer = new FileWriter(xmlFile1)) {
            writer.write(xmlContent1);
        }

        File xmlFile2 = reportsDir.resolve("TEST-Test2.xml").toFile();
        String xmlContent2 = """
                <?xml version="1.0" encoding="UTF-8"?>
                <testsuite name="Test2" tests="3" failures="0" errors="1" skipped="0">
                </testsuite>
                """;
        try (FileWriter writer = new FileWriter(xmlFile2)) {
            writer.write(xmlContent2);
        }

        TestResult result = parser.parse(taskDir, "test-task");

        assertEquals(8, result.getTestsTotal());
        assertEquals(6, result.getTestPassed());
    }

    @Test
    void testParseWithNoReportsDirectory() {
        TestResult result = parser.parse(taskDir, "test-task");

        assertEquals("test-task", result.getTaskId());
        assertTrue(result.isBuildSuccess());
        assertEquals("OK", result.getStatus());
        assertEquals(0, result.getTestPassed());
        assertEquals(0, result.getTestsTotal());
    }

    @Test
    void testParseWithEmptyReportsDirectory() throws IOException {
        Path reportsDir = taskDir.toPath().resolve("build/test-results/test");
        Files.createDirectories(reportsDir);

        TestResult result = parser.parse(taskDir, "test-task");

        assertEquals(0, result.getTestPassed());
        assertEquals(0, result.getTestsTotal());
    }

    @Test
    void testParseWithMalformedXml() throws IOException {
        Path reportsDir = taskDir.toPath().resolve("build/test-results/test");
        Files.createDirectories(reportsDir);

        File xmlFile = reportsDir.resolve("TEST-Bad.xml").toFile();
        String malformedXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <testsuite name="Bad" tests="10"
                """;

        try (FileWriter writer = new FileWriter(xmlFile)) {
            writer.write(malformedXml);
        }

        TestResult result = parser.parse(taskDir, "test-task");

        assertNotNull(result);
    }

    @Test
    void testParseWithZeroTests() throws IOException {
        Path reportsDir = taskDir.toPath().resolve("build/test-results/test");
        Files.createDirectories(reportsDir);

        File xmlFile = reportsDir.resolve("TEST-Zero.xml").toFile();
        String xmlContent = """
                <?xml version="1.0" encoding="UTF-8"?>
                <testsuite name="ZeroTest" tests="0" failures="0" errors="0" skipped="0">
                </testsuite>
                """;
        try (FileWriter writer = new FileWriter(xmlFile)) {
            writer.write(xmlContent);
        }

        TestResult result = parser.parse(taskDir, "test-task");

        assertEquals(0, result.getTestPassed());
        assertEquals(0, result.getTestsTotal());
    }

    @Test
    void testParseWithNestedReportsDirectory() throws IOException {
        Path reportsDir = taskDir.toPath().resolve("build/test-results/test");
        Files.createDirectories(reportsDir);

        Path nestedDir = reportsDir.resolve("nested");
        Files.createDirectories(nestedDir);

        File nestedXml = nestedDir.resolve("TEST-Nested.xml").toFile();
        String xmlContent = """
                <?xml version="1.0" encoding="UTF-8"?>
                <testsuite tests="10" failures="0" errors="0" skipped="0">
                </testsuite>
                """;
        try (FileWriter writer = new FileWriter(nestedXml)) {
            writer.write(xmlContent);
        }

        File rootXml = reportsDir.resolve("TEST-Root.xml").toFile();
        try (FileWriter writer = new FileWriter(rootXml)) {
            writer.write(xmlContent);
        }

        TestResult result = parser.parse(taskDir, "test-task");

        assertEquals(10, result.getTestsTotal());
    }
}
