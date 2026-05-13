package ru.nsu.g.a.vybortseva.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.g.a.vybortseva.model.TestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class TestServiceTest {

    @TempDir
    Path tempDir;

    private TestService testService;
    private File taskDir;

    @BeforeEach
    void setUp() throws IOException {
        testService = new TestService();
        taskDir = tempDir.toFile();

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            File gradlew = new File(taskDir, "gradlew.bat");
            Files.writeString(gradlew.toPath(), "@echo off\nexit 0");
            gradlew.setExecutable(true);
        } else {
            File gradlew = new File(taskDir, "gradlew");
            Files.writeString(gradlew.toPath(), "#!/bin/bash\nexit 0");
            gradlew.setExecutable(true);
        }
    }

    @Test
    void testRunTestsAllSuccessful() throws IOException {
        createMockGradleSuccess();

        Path reportsDir = taskDir.toPath().resolve("build/test-results/test");
        Files.createDirectories(reportsDir);
        File xmlFile = reportsDir.resolve("TEST-Success.xml").toFile();
        String xmlContent = """
                <?xml version="1.0" encoding="UTF-8"?>
                <testsuite name="TestSuite" tests="10" failures="0" errors="0" skipped="0">
                </testsuite>
                """;
        Files.writeString(xmlFile.toPath(), xmlContent);

        TestResult result = testService.runTests(taskDir, "test-task");

        assertEquals("test-task", result.getTaskId());
        assertTrue(result.isBuildSuccess());
        assertEquals("OK", result.getStatus());
        assertEquals(10, result.getTestPassed());
        assertEquals(10, result.getTestsTotal());
    }

    @Test
    void testRunTestsWithCompileError() {
        createMockGradleFailure("classes");

        TestResult result = testService.runTests(taskDir, "test-task");

        assertEquals("test-task", result.getTaskId());
        assertFalse(result.isBuildSuccess());
        assertEquals("COMPILE_ERROR", result.getStatus());
        assertEquals(0, result.getTestPassed());
        assertEquals(0, result.getTestsTotal());
    }

    @Test
    void testRunTestsWithTestError() throws IOException {
        createMockGradleBehavior(
                true,
                true,
                true,
                false
        );

        File initScript = new File("google_style.gradle");
        if (!initScript.exists()) {
            Files.writeString(initScript.toPath(), "// Mock checkstyle config");
        }

        TestResult result = testService.runTests(taskDir, "test-task");

        assertEquals("test-task", result.getTaskId());
        assertFalse(result.isBuildSuccess());
        assertEquals("TEST_ERROR", result.getStatus());

        initScript.delete();
    }

    @Test
    void testRunTestsWithNonExistentTaskDir() {
        File nonExistentDir = new File(tempDir.toFile(), "non-existent");

        TestResult result = testService.runTests(nonExistentDir, "test-task");

        assertEquals("test-task", result.getTaskId());
        assertFalse(result.isBuildSuccess());
        assertEquals("COMPILE_ERROR", result.getStatus());
    }

    @Test
    void testRunTestsWithPartialTestPass() throws IOException {
        createMockGradleSuccess();

        Path reportsDir = taskDir.toPath().resolve("build/test-results/test");
        Files.createDirectories(reportsDir);
        File xmlFile = reportsDir.resolve("TEST-Partial.xml").toFile();
        String xmlContent = """
                <?xml version="1.0" encoding="UTF-8"?>
                <testsuite name="TestSuite" tests="10" failures="3" errors="1" skipped="1">
                </testsuite>
                """;
        Files.writeString(xmlFile.toPath(), xmlContent);

        File initScript = new File("google_style.gradle");
        if (!initScript.exists()) {
            Files.writeString(initScript.toPath(), "// Mock checkstyle config");
        }

        TestResult result = testService.runTests(taskDir, "test-task");

        assertEquals(5, result.getTestPassed());
        assertEquals(10, result.getTestsTotal());
        assertEquals("OK", result.getStatus());

        initScript.delete();
    }

    private void createMockGradleSuccess() {
        createMockGradleBehavior(true, true, true, true);
    }

    private void createMockGradleFailure(String failingTask) {
        boolean classesSuccess = !"classes".equals(failingTask);
        boolean checkstyleSuccess = !"checkstyleMain".equals(failingTask);
        boolean javadocSuccess = !"javadoc".equals(failingTask);
        boolean testSuccess = !"test".equals(failingTask);

        createMockGradleBehavior(classesSuccess, checkstyleSuccess, javadocSuccess, testSuccess);
    }

    private void createMockGradleBehavior(boolean classesOk, boolean checkstyleOk, boolean javadocOk, boolean testOk) {
        String gradleScript = createGradleScript(classesOk, checkstyleOk, javadocOk, testOk);

        String executable = System.getProperty("os.name").toLowerCase().contains("win") ? "gradlew.bat" : "gradlew";
        File gradlew = new File(taskDir, executable);

        try {
            Files.writeString(gradlew.toPath(), gradleScript);
            gradlew.setExecutable(true);
        } catch (IOException e) {
            fail("Failed to create mock gradlew: " + e.getMessage());
        }
    }

    private String createGradleScript(boolean classesOk, boolean checkstyleOk, boolean javadocOk, boolean testOk) {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            StringBuilder script = new StringBuilder("@echo off\n");
            if (!classesOk) {
                script.append("if \"%1\"==\"classes\" exit 1\n");
            }
            if (!checkstyleOk) {
                script.append("if \"%1\"==\"checkstyleMain\" exit 1\n");
            }
            if (!javadocOk) {
                script.append("if \"%1\"==\"javadoc\" exit 1\n");
            }
            if (!testOk) {
                script.append("if \"%1\"==\"test\" exit 1\n");
            }
            script.append("exit 0\n");
            return script.toString();
        } else {
            StringBuilder script = new StringBuilder("#!/bin/bash\n");
            if (!classesOk) {
                script.append("if [ \"$1\" = \"classes\" ]; then exit 1; fi\n");
            }
            if (!checkstyleOk) {
                script.append("if [ \"$1\" = \"checkstyleMain\" ]; then exit 1; fi\n");
            }
            if (!javadocOk) {
                script.append("if [ \"$1\" = \"javadoc\" ]; then exit 1; fi\n");
            }
            if (!testOk) {
                script.append("if [ \"$1\" = \"test\" ]; then exit 1; fi\n");
            }
            script.append("exit 0\n");
            return script.toString();
        }
    }
}