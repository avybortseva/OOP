package ru.nsu.g.a.vybortseva.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.g.a.vybortseva.model.Config;
import ru.nsu.g.a.vybortseva.model.Group;
import ru.nsu.g.a.vybortseva.model.Student;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

class GitServiceTest {

    @TempDir
    Path tempDir;

    private GitService gitService;
    private Config config;
    private Group testGroup;
    private Student testStudent;

    @BeforeEach
    void setUp() {
        gitService = new GitService(tempDir.toString());
        config = new Config();
        testGroup = new Group(24213);
        testStudent = new Student("Test Student", "test-git", "https://github.com/test/repo");
        testGroup.addStudent(testStudent);
        config.getGroups().add(testGroup);
    }

    @Test
    void testGitServiceConstructor() {
        assertNotNull(gitService);
    }

    @Test
    void testUpdateAllWithNonExistentRepo() {
        assertDoesNotThrow(() -> gitService.updateAll(config));
    }

    @Test
    void testGetCommitDateWithNonExistentPath() {
        Path fakePath = tempDir.resolve("fake/path");
        LocalDate result = gitService.getCommitDate("Task_1_1_1", fakePath);

        assertNull(result);
    }

    @Test
    void testGetCommitDateWithNonExistentTask() throws IOException, InterruptedException {
        Path repoPath = tempDir.resolve("test-repo");
        Files.createDirectories(repoPath);

        ProcessBuilder pb = new ProcessBuilder("git", "init");
        pb.directory(repoPath.toFile());
        pb.start().waitFor();

        LocalDate result = gitService.getCommitDate("NonExistentTask", repoPath);

        assertNull(result);
    }

    @Test
    void testGetCommitHistoryReturnsEmptyListForNonExistentTask() throws IOException, InterruptedException {
        Path repoPath = tempDir.resolve("test-repo");
        Files.createDirectories(repoPath);

        ProcessBuilder pb = new ProcessBuilder("git", "init");
        pb.directory(repoPath.toFile());
        pb.start().waitFor();

        List<OffsetDateTime> commits = gitService.getCommitHistory(
                "NonExistentTask", repoPath,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31));

        assertNotNull(commits);
        assertTrue(commits.isEmpty());
    }

    @Test
    void testIsActiveInWeekReturnsFalseForEmptyRepo() throws IOException, InterruptedException {
        Path repoPath = tempDir.resolve("test-repo");
        Files.createDirectories(repoPath);

        ProcessBuilder pb = new ProcessBuilder("git", "init");
        pb.directory(repoPath.toFile());
        pb.start().waitFor();

        boolean active = gitService.isActiveInWeek(
                repoPath, "Task_1_1_1",
                LocalDate.of(2025, 1, 1));

        assertFalse(active);
    }

    @Test
    void testRunCommandWithOutputHandlesNullDirectory() {
        boolean result = gitService.runCommandWithOutput(null, List.of("echo", "test"), null);
        assertFalse(result);
    }

    @Test
    void testGetCommitHistoryWithEmptyDateRange() throws IOException, InterruptedException {
        Path repoPath = tempDir.resolve("test-repo");
        Files.createDirectories(repoPath);

        ProcessBuilder pb = new ProcessBuilder("git", "init");
        pb.directory(repoPath.toFile());
        pb.start().waitFor();

        List<OffsetDateTime> commits = gitService.getCommitHistory(
                "Task_1_1_1", repoPath,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 1));

        assertNotNull(commits);
        assertTrue(commits.isEmpty());
    }

    @Test
    void testProcessStudentHandlesInvalidUrl() {
        Student invalidStudent = new Student("Invalid", "invalid-git", "https://github.com/invalid/repo");

        assertDoesNotThrow(() -> {
            gitService.updateAll(config);
        });
    }

    @Test
    void testGetCommitDateReturnsNullForEmptyRepo() throws IOException, InterruptedException {
        Path repoPath = tempDir.resolve("empty-repo");
        Files.createDirectories(repoPath);

        ProcessBuilder pb = new ProcessBuilder("git", "init");
        pb.directory(repoPath.toFile());
        pb.start().waitFor();

        Path taskPath = repoPath.resolve("Task_1_1_1");
        Files.createDirectories(taskPath);

        LocalDate result = gitService.getCommitDate("Task_1_1_1", repoPath);

        assertNull(result);
    }

    @Test
    void testRunCommandWithOutputWithInvalidCommand() {
        boolean result = gitService.runCommandWithOutput(
                tempDir.toFile(),
                List.of("nonexistentcommand"),
                null);
        assertFalse(result);
    }

    @Test
    void testIsActiveInWeekWithNullTask() throws IOException, InterruptedException {
        Path repoPath = tempDir.resolve("test-repo");
        Files.createDirectories(repoPath);

        ProcessBuilder pb = new ProcessBuilder("git", "init");
        pb.directory(repoPath.toFile());
        pb.start().waitFor();

        boolean active = gitService.isActiveInWeek(
                repoPath, null,
                LocalDate.of(2025, 1, 1));

        assertFalse(active);
    }

    @Test
    void testUpdateAllWithEmptyConfig() {
        Config emptyConfig = new Config();
        assertDoesNotThrow(() -> gitService.updateAll(emptyConfig));
    }
}
