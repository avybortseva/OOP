package ru.nsu.g.a.vybortseva;

import ru.nsu.g.a.vybortseva.model.*;
import ru.nsu.g.a.vybortseva.service.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void main(String[] args) {
        String configPath = "course.groovy";
        String rootReposDir = "repos";

        Map<Integer, Map<String, Map<String, ReportGenerator.StudentTaskData>>> allData = new LinkedHashMap<>();

        try {
            ConfigLoader loader = new ConfigLoader();
            Config config = loader.load(configPath);

            GitService gitService = new GitService(rootReposDir);
            gitService.updateAll(config);

            TestService testService = new TestService();
            GradingService gradingService = new GradingService();

            for (Group group : config.getGroups()) {
                System.out.println("\n=== Проверяется группа: " + group.getNumber() + " ===");

                Map<String, Map<String, ReportGenerator.StudentTaskData>> groupData = new LinkedHashMap<>();

                for (Student student : group.getGroup()) {
                    System.out.println("  Студент: " + student.getName());

                    Map<String, ReportGenerator.StudentTaskData> studentTasks = new LinkedHashMap<>();

                    for (String taskId : config.getAssignmentsForGroup(group.getNumber())) {
                        System.out.print("    Проверяется " + taskId + "... ");

                        Path repoPath = Paths.get(rootReposDir, String.valueOf(group.getNumber()), student.getGitName());
                        String folderName = "Task_" + taskId.replace(".", "_");
                        File taskDir = new File(repoPath.toFile(), folderName);

                        ReportGenerator.StudentTaskData data = new ReportGenerator.StudentTaskData();
                        data.taskId = taskId;

                        Task taskInfo = config.getTasks().stream()
                                .filter(t -> t.getId().equals(taskId))
                                .findFirst()
                                .orElse(null);

                        if (taskInfo != null) {
                            data.softDeadline = taskInfo.getSoftDeadline();
                            data.hardDeadline = taskInfo.getHardDeadline();
                        }

                        if (!taskDir.exists()) {
                            System.out.println("папка не найдена");
                            data.buildSuccess = false;
                            data.docSuccess = false;
                            data.styleSuccess = false;
                            data.testPassed = 0;
                            data.testTotal = 0;
                            data.commitDate = null;
                            data.grade = new GradingService.GradeBreakdown(0, 0, 0, 0);
                        } else {
                            LocalDate commitDate = gitService.getCommitDate(folderName, repoPath);
                            data.commitDate = commitDate;

                            TestResult testResult = testService.runTests(taskDir, taskId);

                            if (taskInfo != null) {
                                GradingService.GradeBreakdown grade = gradingService.calculateScoreWithBreakdown(
                                        testResult, taskInfo, config.getBonuses(),
                                        student.getGitName(), commitDate
                                );
                                data.grade = grade;

                                data.buildSuccess = testResult.isBuildSuccess();
                                data.docSuccess = "OK".equals(testResult.getStatus()) ||
                                        "STYLE_ERROR".equals(testResult.getStatus());
                                data.styleSuccess = !"STYLE_ERROR".equals(testResult.getStatus());
                                data.testPassed = testResult.getTestPassed();
                                data.testTotal = testResult.getTestsTotal();

                                System.out.println("готово (дата сдачи: " + (commitDate != null ? commitDate.format(DATE_FORMATTER) : "нет") + ")");
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
            System.err.println("Критическая ошибка:");
            e.printStackTrace();
        }
    }
}
