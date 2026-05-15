package ru.nsu.g.a.vybortseva.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.g.a.vybortseva.model.TestResult;

/**
 * Service for sequential checking of task.
 */
public class TestService {

    /**
     * Method for running of the tests.
     */
    public TestResult runTests(File taskDir, String taskId) {
        if (!runGradleTask(taskDir, "classes")) {
            return new TestResult(taskId, false, 0, 0, "COMPILE_ERROR");
        }

        File initScript = new File("google_style.gradle").getAbsoluteFile();
        if (!runGradleTask(taskDir, "--init-script", initScript.getAbsolutePath(),
                "checkstyleMain")) {
            System.out.println("      [STOP] Нарушение Google Java Style.");
            return new TestResult(taskId, false, 0, 0, "STYLE_ERROR");
        }
        System.out.println("      [OK] Стиль соответствует Google Java Style.");

        if (!runGradleTask(taskDir, "javadoc")) {
            return new TestResult(taskId, false, 0, 0, "JAVADOC_ERROR");
        }

        if (!runGradleTask(taskDir, "test")) {
            return new TestResult(taskId, false, 0, 0, "TEST_ERROR");
        }

        TestReportParser parser = new TestReportParser();
        TestResult parsed = parser.parse(taskDir, taskId);
        return new TestResult(taskId, true, parsed.getTestPassed(), parsed.getTestsTotal(), "OK");
    }

    private boolean runGradleTask(File projectDir, String... tasks) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String executable = os.contains("win") ? "gradlew.bat" : "./gradlew";

            List<String> command = new ArrayList<>();
            command.add(new File(projectDir, executable).getAbsolutePath());
            command.addAll(List.of(tasks));

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(projectDir);
            pb.environment().put("JAVA_HOME", System.getProperty("java.home"));

            pb.redirectErrorStream(true);
            Process process = pb.start();

            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }
}
