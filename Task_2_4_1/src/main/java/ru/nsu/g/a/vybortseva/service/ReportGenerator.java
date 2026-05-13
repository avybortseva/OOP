package ru.nsu.g.a.vybortseva.service;

import ru.nsu.g.a.vybortseva.model.CheckPoint;
import ru.nsu.g.a.vybortseva.model.Config;
import ru.nsu.g.a.vybortseva.model.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Service for generating HTML reports from check results.
 */
public class ReportGenerator {
    private static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Data container for student task results.
     */
    public static class StudentTaskData {
        public String taskId;
        public LocalDate commitDate;
        public LocalDate softDeadline;
        public LocalDate hardDeadline;
        public boolean buildSuccess;
        public boolean docSuccess;
        public boolean styleSuccess;
        public int testPassed;
        public int testTotal;
        public GradingService.GradeBreakdown grade;
    }

    /**
     * Generates HTML report from collected data.
     */
    public void generateHtmlReport(Map<Integer, Map<String, Map<String,
            StudentTaskData>>> allData, Config config) throws IOException {
        StringBuilder html = new StringBuilder();
        LocalDate today = LocalDate.now();

        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <title>Отчет по проверке</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; }\n");
        html.append("        h2 { color: #333; margin-top: 30px; }\n");
        html.append("        h3 { color: #555; margin-top: 20px; }\n");
        html.append("        table { border-collapse: collapse; margin: 10px 0; width: 100%; }\n");
        html.append("        th, td { border: 1px solid #999; padding: "
                + "8px 10px; text-align: center; }\n");
        html.append("        th { background: #f0f0f0; font-weight: bold; }\n");
        html.append("        .ok { color: green; font-weight: bold; }\n");
        html.append("        .fail { color: red; font-weight: bold; }\n");
        html.append("        .date { font-family: monospace; font-size: 12px; }\n");
        html.append("        .note { font-size: 12px; color: #666; }\n");
        html.append("        .checkpoint-pass { color: green; }\n");
        html.append("        .checkpoint-fail { color: red; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <h1>Отчет по проверке лабораторных работ</h1>\n");
        html.append("    <p class='note'>Дата формирования отчета: ").
                append(today.format(DATE_FORMATTER)).append("</p>\n");

        for (var groupEntry : allData.entrySet()) {
            int groupNumber = groupEntry.getKey();
            Map<String, Map<String, StudentTaskData>> groupData = groupEntry.getValue();

            html.append("<h2>Группа ").append(groupNumber).append("</h2>\n");

            Set<String> taskIds = new LinkedHashSet<>();
            for (var studentData : groupData.values()) {
                taskIds.addAll(studentData.keySet());
            }

            for (String taskId : taskIds) {
                generateTaskTable(html, taskId, groupData, config);
            }

            generateSummaryTable(html, groupNumber, groupData, taskIds, config, today);
        }

        html.append("</body>\n");
        html.append("</html>\n");

        Files.write(Paths.get("report.html"), html.toString().getBytes("UTF-8"));
        System.out.println("\nОтчет сохранен: report.html");
    }

    private void generateTaskTable(StringBuilder html, String taskId,
                                   Map<String, Map<String, StudentTaskData>> groupData,
                                   Config config) {
        Task task = config.getTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst().orElse(null);

        String taskTitle = task != null ? task.getTitle() : taskId;
        String softDate = task != null && task.getSoftDeadline() != null
                ? task.getSoftDeadline().format(DATE_FORMATTER) : "не указан";
        String hardDate = task != null && task.getHardDeadline() != null
                ? task.getHardDeadline().format(DATE_FORMATTER) : "не указан";

        html.append("<h3>Лабораторная ").append(taskId).
                append(" (").append(taskTitle).append(")</h3>\n");
        html.append("<p class='note'>Мягкий дедлайн: ").
                append(softDate).append(" | Жесткий дедлайн: ").append(hardDate).append("</p>\n");

        html.append("<table>\n");
        html.append("    <thead>\n");
        html.append("        <tr>\n");
        html.append("            <th>Студент</th>\n");
        html.append("            <th>Дата сдачи</th>\n");
        html.append("            <th>Сборка</th>\n");
        html.append("            <th>Docs</th>\n");
        html.append("            <th>Style</th>\n");
        html.append("            <th>Тесты</th>\n");
        html.append("            <th>База</th>\n");
        html.append("            <th>МД</th>\n");
        html.append("            <th>ЖД</th>\n");
        html.append("            <th>Бонус</th>\n");
        html.append("            <th>Итого</th>\n");
        html.append("        </tr>\n");
        html.append("    </thead>\n");
        html.append("    <tbody>\n");

        for (var studentEntry : groupData.entrySet()) {
            String studentName = studentEntry.getKey();
            StudentTaskData data = studentEntry.getValue().get(taskId);

            if (data != null && data.grade != null) {
                String commitDateStr = data.commitDate != null
                        ? data.commitDate.format(DATE_FORMATTER) : "—";
                String testStr = data.testPassed + "/" + (data.testTotal - data.testPassed);

                html.append("        <tr>\n");
                html.append("            <td>").append(studentName).append("</td>\n");
                html.append("            <td class='date'>").append(commitDateStr).
                        append("</td>\n");
                html.append("            <td class='").append(data.buildSuccess ? "ok"
                        : "fail").append("'>").append(data.buildSuccess ? "+" : "-").
                        append("</td>\n");
                html.append("            <td class='").append(data.docSuccess ? "ok"
                        : "fail").append("'>").append(data.docSuccess ? "+" : "-").
                        append("</td>\n");
                html.append("            <td class='").append(data.styleSuccess ? "ok"
                        : "fail").append("'>").append(data.styleSuccess ? "+" : "-").
                        append("</td>\n");
                html.append("            <td>").append(testStr).append("</td>\n");
                html.append("            <td>").append(String.format("%.1f",
                        data.grade.basePoints)).append("</td>\n");
                html.append("            <td>").append(String.format("%.1f",
                        data.grade.softPoints)).append("</td>\n");
                html.append("            <td>").append(String.format("%.1f",
                        data.grade.hardPoints)).append("</td>\n");
                html.append("            <td>").append(String.format("%.1f",
                        data.grade.bonusPoints)).append("</td>\n");
                html.append("            <td><b>").append(String.format("%.1f",
                        data.grade.totalPoints)).append("</b></td>\n");
                html.append("        </tr>\n");
            }
        }
        html.append("    </tbody>\n");
        html.append("</table>\n");
    }

    private void generateSummaryTable(StringBuilder html, int groupNumber,
                                      Map<String, Map<String, StudentTaskData>> groupData,
                                      Set<String> taskIds, Config config, LocalDate today) {
        html.append("<h3>Сводная таблица по группе ").append(groupNumber).append("</h3>\n");
        html.append("<p class='note'>Дата формирования: ").
                append(today.format(DATE_FORMATTER)).append("</p>\n");

        html.append("<table border='1'>\n");
        html.append("    <thead>\n");

        html.append("        <tr>\n");
        html.append("            <th rowspan='2'>Студент</th>\n");
        html.append("            <th colspan='").
                append(taskIds.size()).append("'>Баллы за задачи</th>\n");
        for (CheckPoint point : config.getPoints()) {
            html.append("            <th rowspan='2'>").append(point.getName()).append("</th>\n");
        }
        html.append("        </tr>\n");

        html.append("        <tr>\n");
        for (String taskId : taskIds) {
            html.append("            <th>").append(taskId).append("</th>\n");
        }
        html.append("        </tr>\n");
        html.append("    </thead>\n");
        html.append("    <tbody>\n");

        for (var studentEntry : groupData.entrySet()) {
            String studentName = studentEntry.getKey();
            Map<String, StudentTaskData> tasks = studentEntry.getValue();

            html.append("        <tr>\n");
            html.append("            <td>").append(studentName).append("</td>\n");

            double total = 0;
            for (String taskId : taskIds) {
                StudentTaskData data = tasks.get(taskId);
                double score = (data != null && data.grade != null) ? data.grade.totalPoints : 0;
                total += score;
                html.append("            <td>").
                        append(String.format("%.1f", score)).append("</td>\n");
            }

            for (CheckPoint point : config.getPoints()) {
                double earnedUpToPoint = calculateEarnedUpToPoint(tasks, taskIds, config, point);
                html.append("            <td>").
                        append(String.format("%.1f", earnedUpToPoint)).append("</td>\n");
            }

            html.append("        </tr>\n");
        }

        html.append("    </tbody>\n");
        html.append("</table>\n");
    }

    private double calculateEarnedUpToPoint(Map<String, StudentTaskData> tasks,
                                            Set<String> taskIds,
                                            Config config,
                                            CheckPoint point) {
        double earned = 0;
        LocalDate pointDate = point.getDate();

        for (String taskId : taskIds) {
            StudentTaskData data = tasks.get(taskId);
            if (data != null && data.grade != null && data.commitDate != null) {
                if (!data.commitDate.isAfter(pointDate)) {
                    earned += data.grade.totalPoints;
                }
            }
        }
        return earned;
    }
}
