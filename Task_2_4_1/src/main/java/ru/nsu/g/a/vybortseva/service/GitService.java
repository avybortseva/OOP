package ru.nsu.g.a.vybortseva.service;

import ru.nsu.g.a.vybortseva.model.Config;
import ru.nsu.g.a.vybortseva.model.Group;
import ru.nsu.g.a.vybortseva.model.Student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GitService {
    private final String rootPath;

    public GitService(String rootPath) {
        this.rootPath = rootPath;
    }

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

    private void processStudent(Student student, int group) throws IOException, InterruptedException {
        Path studentPath = Paths.get(rootPath, String.valueOf(group), student.getGitName());
        File directory = studentPath.toFile();

        if (directory.exists()) {
            runCommand(directory, "git", "pull");
        } else {
            Files.createDirectories(studentPath.getParent());
            runCommand(studentPath.getParent().toFile(), "git", "clone", student.getRepoUrl(), student.getGitName());
        }
    }

    private void runCommand(File directory, String... command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(directory);

        builder.inheritIO();
        Process process = builder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("ran command error");
        }
    }
}
