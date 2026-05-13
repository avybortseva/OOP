package ru.nsu.g.a.vybortseva.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ru.nsu.g.a.vybortseva.model.Config;
import ru.nsu.g.a.vybortseva.model.Group;
import ru.nsu.g.a.vybortseva.model.Student;

/**
 * Service for managing git operations like cloning and updating student repositories.
 */
public class GitService {
    private final String rootPath;

    /**
     * Constructs a GitService with a specified base directory.
     */
    public GitService(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * Iterates through all groups and students to synchronize their repositories.
     */
    public void updateAll(Config config) {
        for (Group group : config.getGroups()) {
            for (Student student : group.getGroup()) {
                try {
                    processStudent(student, group.getNumber());
                } catch (Exception e) {
                    System.out.println("updating student error");
                }
            }
        }
    }

    /**
     * Clones or pulls the repository for a specific student.
     */
    private void processStudent(Student student, int group) throws IOException,
            InterruptedException {
        Path studentPath = Paths.get(rootPath, String.valueOf(group), student.getGitName());
        File directory = studentPath.toFile();

        if (directory.exists()) {
            runCommand(directory, "git", "pull");
        } else {
            Files.createDirectories(studentPath.getParent());
            runCommand(studentPath.getParent().toFile(), "git", "clone", student.getRepoUrl(),
                    student.getGitName());
        }
    }

    /**
     * Executes a system command in a specific directory.
     */
    private void runCommand(File directory, String... command) throws IOException,
            InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(directory);

        builder.inheritIO();
        Process process = builder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("ran command error");
        }
    }

    public LocalDate getCommitDate(String taskId, Path repoPath) {
        Path taskPath = repoPath.resolve(taskId);
        System.out.println("Task path: " + taskPath.toAbsolutePath());
        System.out.println("Task path exists: " + Files.exists(taskPath));

        if (!Files.exists(taskPath)) {
            System.out.println("Task path does NOT exist!");
            return null;
        }

        List<String> command = List.of("git", "log", "-1", "--format=%cs", "--", taskId);
        System.out.println("Running command: " + command);

        String[] dateString = {null};

        boolean success = runCommandWithOutput(repoPath.toFile(), command,
                line -> {
                    System.out.println("Git output line: '" + line + "'");
                    dateString[0] = line.trim();
                });

        System.out.println("Command success: " + success);
        System.out.println("Date string: " + dateString[0]);

        if (success && dateString[0] != null && !dateString[0].isEmpty()) {
            try {
                return LocalDate.parse(dateString[0]);
            } catch (Exception e) {
                System.out.println("Parse error: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * Getting list of commits for the period.
     */
    public List<OffsetDateTime> getCommitHistory(String taskId, Path repoPath, LocalDate from, LocalDate to) {
        List<OffsetDateTime> commitDates = new ArrayList<>();

        List<String> command = List.of(
                "git", "log",
                "--format=%cI",
                "--since=" + from.toString(),
                "--until=" + to.toString(),
                "--",
                taskId
        );

        runCommandWithOutput(repoPath.toFile(), command, line -> {
            if (line != null && !line.isBlank()) {
                try {
                    commitDates.add(OffsetDateTime.parse(line.trim(),
                            DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                } catch (Exception e) {
                    // игнорируем
                }
            }
        });

        return commitDates;
    }

    /**
     * Check if the student was active in week.
     */
    public boolean isActiveInWeek(Path repoPath, String taskId, LocalDate startOfWeek) {
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        List<OffsetDateTime> commits = getCommitHistory(taskId, repoPath, startOfWeek, endOfWeek);
        return !commits.isEmpty();
    }

    /**
     * Method for executing command with output.
     */
    public boolean runCommandWithOutput(File directory, List<String> command, java.util.function.Consumer<String> outputHandler) {
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.directory(directory);
            builder.redirectErrorStream(true);

            Process process = builder.start();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (outputHandler != null) {
                        outputHandler.accept(line);
                    }
                }
            }
            return process.waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }
}
