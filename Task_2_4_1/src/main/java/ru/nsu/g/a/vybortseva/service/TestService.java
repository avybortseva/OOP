package ru.nsu.g.a.vybortseva.service;
import java.io.File;
import java.io.IOException;

/**
 * Service for executing automated tests within student repositories.
 */
public class TestService {
    /**
     * Executes Gradle tests for the specified student repository directory.
     */
    public boolean runTests(File studentRepoDir) {
        if (studentRepoDir == null) {
            return false;
        }

        String os = System.getProperty("os.name").toLowerCase();
        String gradlewExecutable = "gradlew.bat";

        File gradlewFile = new File(studentRepoDir, gradlewExecutable);

        if (!gradlewFile.exists()) {
            System.err.println("Ошибка: gradlew не найден в " + studentRepoDir.getAbsolutePath());
            return false;
        }

        try {
            ProcessBuilder pb = new ProcessBuilder(gradlewFile.getAbsolutePath(), "test");
            pb.directory(studentRepoDir);

            String javaHome = System.getProperty("java.home");
            pb.environment().put("JAVA_HOME", javaHome);

            pb.inheritIO();
            Process process = pb.start();
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}