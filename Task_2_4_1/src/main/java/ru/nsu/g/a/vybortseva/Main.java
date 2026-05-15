package ru.nsu.g.a.vybortseva;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import ru.nsu.g.a.vybortseva.model.Config;
import ru.nsu.g.a.vybortseva.model.Group;
import ru.nsu.g.a.vybortseva.model.Student;
import ru.nsu.g.a.vybortseva.model.Task;
import ru.nsu.g.a.vybortseva.model.TestResult;
import ru.nsu.g.a.vybortseva.service.ConfigLoader;
import ru.nsu.g.a.vybortseva.service.GitService;
import ru.nsu.g.a.vybortseva.service.GradingService;
import ru.nsu.g.a.vybortseva.service.ReportGenerator;
import ru.nsu.g.a.vybortseva.service.TestService;

/**
 * Main class.
 */
public class Main {
    private static final DateTimeFormatter
            DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Main method.
     */
    public static void main(String[] args) {
        String configPath = "course.groovy";
        String rootReposDir = "repos";

        Map<Integer, Map<String, Map<String,
                ReportGenerator.StudentTaskData>>> allData = new LinkedHashMap<>();

        try {
            ConfigLoader loader = new ConfigLoader();

            Config config = loader.load(configPath);

            GitService gitService = new GitService(rootReposDir);
            gitService.updateAll(config);

            TestService testService = new TestService();
            GradingService gradingService = new GradingService();

            for (Group group : config.getGroups()) {
                System.out.println("\n=== Проверяется группа: " + group.getNumber() + " ===");

                Map<String, Map<String, ReportGenerator.StudentTaskData>> groupData
                        = new LinkedHashMap<>();

                for (Student student : group.getGroup()) {
                    System.out.println("  Студент: " + student.getName());

                    Map<String, ReportGenerator.StudentTaskData> studentTasks
                            = new LinkedHashMap<>();

                    for (String taskId : config.getAssignmentsForGroup(group.getNumber())) {
                        System.out.print("    Проверяется " + taskId + "... ");

                        Path repoPath = Paths.get(rootReposDir,
                                String.valueOf(group.getNumber()), student.getGitName());
                        String folderName = "Task_" + taskId.replace(".", "_");
                        File taskDir = new File(repoPath.toFile(), folderName);

                        Task taskInfo = config.getTasks().stream()
                                .filter(t -> t.getId().equals(taskId))
                                .findFirst()
                                .orElse(null);

                        ReportGenerator.StudentTaskData data;

                        if (!taskDir.exists()) {
                            System.out.println("папка не найдена");
                            data = gradingService.createEmptyTaskData(student, taskInfo);
                        } else {
                            LocalDate commitDate = gitService.getCommitDate(folderName, repoPath);
                            TestResult testResult = testService.runTests(taskDir, taskId);

                            if (taskInfo != null) {
                                GradingService.GradeBreakdown grade
                                        = gradingService.calculateScoreWithBreakdown(
                                        testResult, taskInfo, config.getBonuses(),
                                        student.getGitName(), commitDate
                                );
                                data = new ReportGenerator.StudentTaskData(
                                        student.getId(),
                                        student.getFullName(),
                                        taskId,
                                        taskInfo.getTitle(),
                                        testResult.isBuildSuccess(),
                                        "OK".equals(testResult.getStatus())
                                                || "STYLE_ERROR".equals(testResult.getStatus()),
                                        !"STYLE_ERROR".equals(testResult.getStatus()),
                                        testResult.getTestPassed(),
                                        testResult.getTestsTotal(),
                                        commitDate,
                                        grade
                                );

                                System.out.println("готово (дата сдачи: "
                                        + (commitDate != null ? commitDate.format(DATE_FORMATTER)
                                        : "нет") + ")");
                            } else {
                                data = gradingService.createEmptyTaskData(student, null);
                            }
                        }

                        studentTasks.put(taskId, data);
                    }

                    groupData.put(student.getName(), studentTasks);
                }

                allData.put(group.getNumber(), groupData);
                System.out.println("  Группа " + group.getNumber() + " проверена");
            }

            System.out.println("\n=== Проверка закончена ===");

            ReportGenerator reportGenerator = new ReportGenerator();
            reportGenerator.generateHtmlReport(allData, config);

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        }
    }
}
