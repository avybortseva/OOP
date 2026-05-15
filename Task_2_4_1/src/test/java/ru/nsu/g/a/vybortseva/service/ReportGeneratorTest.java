package ru.nsu.g.a.vybortseva.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.g.a.vybortseva.model.CheckPoint;
import ru.nsu.g.a.vybortseva.model.Config;
import ru.nsu.g.a.vybortseva.model.Task;

class ReportGeneratorTest {

    @TempDir
    Path tempDir;

    private ReportGenerator reportGenerator;
    private Config config;
    private Map<Integer, Map<String, Map<String, ReportGenerator.StudentTaskData>>> allData;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGenerator();
        config = new Config();
        allData = new LinkedHashMap<>();

        Task task = new Task("2_1_1", "Test Task", "2026-05-01", "2026-05-10");
        config.getTasks().add(task);

        config.getPoints().add(new CheckPoint("First Month", "2026-03-01"));
        config.getPoints().add(new CheckPoint("Middle Semester", "2026-04-15"));
    }

    @Test
    void testGenerateHtmlReportCreatesFile() throws IOException {
        ReportGenerator.StudentTaskData data = new ReportGenerator.StudentTaskData(
                "001",                    // studentId
                "Test Student",           // studentName
                "2_1_1",                  // taskId
                "Test Task",              // taskName
                true,                     // buildSuccess
                true,                     // docSuccess
                true,                     // styleSuccess
                10,                       // testPassed
                10,                       // testTotal
                LocalDate.of(2026, 2, 15), // commitDate
                new GradingService.GradeBreakdown(1.0, 0.5, 0.5, 0.0)  // grade
        );

        Map<String, ReportGenerator.StudentTaskData> studentTasks = new LinkedHashMap<>();
        studentTasks.put("2_1_1", data);

        Map<String, Map<String, ReportGenerator.StudentTaskData>> groupData = new LinkedHashMap<>();
        groupData.put("Test Student", studentTasks);
        allData.put(24213, groupData);

        Path originalPath = Path.of("report.html");

        reportGenerator.generateHtmlReport(allData, config);

        assertTrue(Files.exists(originalPath));
        Files.deleteIfExists(originalPath);
    }

    @Test
    void testGenerateHtmlReportWithMultipleStudents() throws IOException {
        ReportGenerator.StudentTaskData data1 = new ReportGenerator.StudentTaskData(
                "001", "Student A", "2_1_1", "Test Task",
                true, true, true, 10, 10,
                LocalDate.of(2026, 2, 15),
                new GradingService.GradeBreakdown(1.0, 0.5, 0.5, 0.0)
        );

        ReportGenerator.StudentTaskData data2 = new ReportGenerator.StudentTaskData(
                "002", "Student B", "2_1_1", "Test Task",
                true, false, false, 5, 10,
                LocalDate.of(2026, 5, 5),
                new GradingService.GradeBreakdown(1.0, 0.0, 0.5, 0.0)
        );

        Map<String, ReportGenerator.StudentTaskData> studentTasks1 = new LinkedHashMap<>();
        studentTasks1.put("2_1_1", data1);

        Map<String, ReportGenerator.StudentTaskData> studentTasks2 = new LinkedHashMap<>();
        studentTasks2.put("2_1_1", data2);

        Map<String, Map<String, ReportGenerator.StudentTaskData>> groupData = new LinkedHashMap<>();
        groupData.put("Student A", studentTasks1);
        groupData.put("Student B", studentTasks2);
        allData.put(24213, groupData);

        assertDoesNotThrow(() -> reportGenerator.generateHtmlReport(allData, config));

        Path reportPath = Path.of("report.html");
        assertTrue(Files.exists(reportPath));
        Files.deleteIfExists(reportPath);
    }

    @Test
    void testGenerateHtmlReportWithStudentWithoutCommit() throws IOException {
        ReportGenerator.StudentTaskData data = new ReportGenerator.StudentTaskData(
                "003", "Student Without Commit", "2_1_1", "Test Task",
                false, false, false, 0, 0,
                null,  // commitDate = null
                new GradingService.GradeBreakdown(0, 0, 0, 0)
        );

        Map<String, ReportGenerator.StudentTaskData> studentTasks = new LinkedHashMap<>();
        studentTasks.put("2_1_1", data);

        Map<String, Map<String, ReportGenerator.StudentTaskData>> groupData = new LinkedHashMap<>();
        groupData.put("Student Without Commit", studentTasks);
        allData.put(24213, groupData);

        assertDoesNotThrow(() -> reportGenerator.generateHtmlReport(allData, config));

        Path reportPath = Path.of("report.html");
        assertTrue(Files.exists(reportPath));
        Files.deleteIfExists(reportPath);
    }

    @Test
    void testGenerateHtmlReportWithMultipleTasks() throws IOException {
        Task task2 = new Task("3_1_1", "Second Task", "2026-06-01", "2026-06-10");
        config.getTasks().add(task2);

        ReportGenerator.StudentTaskData data1 = new ReportGenerator.StudentTaskData(
                "001", "Student", "2_1_1", "Test Task",
                true, true, true, 10, 10,
                LocalDate.of(2026, 2, 15),
                new GradingService.GradeBreakdown(1.0, 0.5, 0.5, 0.0)
        );

        ReportGenerator.StudentTaskData data2 = new ReportGenerator.StudentTaskData(
                "001", "Student", "3_1_1", "Second Task",
                true, true, true, 8, 10,
                LocalDate.of(2026, 5, 20),
                new GradingService.GradeBreakdown(1.0, 0.5, 0.5, 0.0)
        );

        Map<String, ReportGenerator.StudentTaskData> studentTasks = new LinkedHashMap<>();
        studentTasks.put("2_1_1", data1);
        studentTasks.put("3_1_1", data2);

        Map<String, Map<String, ReportGenerator.StudentTaskData>> groupData = new LinkedHashMap<>();
        groupData.put("Student", studentTasks);
        allData.put(24213, groupData);

        assertDoesNotThrow(() -> reportGenerator.generateHtmlReport(allData, config));

        Path reportPath = Path.of("report.html");
        assertTrue(Files.exists(reportPath));
        Files.deleteIfExists(reportPath);
    }

    @Test
    void testStudentTaskDataFields() {
        LocalDate commitDate = LocalDate.now();
        GradingService.GradeBreakdown grade = new GradingService.GradeBreakdown(1.0, 0.5, 0.5, 0.0);
        ReportGenerator.StudentTaskData data = new ReportGenerator.StudentTaskData(
                "001",           // studentId
                "Test Student",  // studentName
                "test-task",     // taskId
                "Test Task",     // taskName
                true,            // buildSuccess
                true,            // docSuccess
                true,            // styleSuccess
                10,              // testPassed
                10,              // testTotal
                commitDate,      // commitDate
                grade            // grade
        );

        assertEquals("001", data.studentId());
        assertEquals("Test Student", data.studentName());
        assertEquals("test-task", data.taskId());
        assertEquals("Test Task", data.taskName());
        assertTrue(data.buildSuccess());
        assertTrue(data.docSuccess());
        assertTrue(data.styleSuccess());
        assertEquals(10, data.testPassed());
        assertEquals(10, data.testTotal());
        assertNotNull(data.commitDate());
        assertNotNull(data.grade());
    }
}
