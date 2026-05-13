package ru.nsu.g.a.vybortseva.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.a.vybortseva.model.Bonus;
import ru.nsu.g.a.vybortseva.model.Task;
import ru.nsu.g.a.vybortseva.model.TestResult;

class GradingServiceTest {

    private GradingService gradingService;
    private Task task;
    private List<Bonus> bonuses;
    private String studentGitName;

    @BeforeEach
    void setUp() {
        gradingService = new GradingService();
        task = new Task("2_1_1", "Test Task", "2026-05-01", "2026-05-10");
        bonuses = new ArrayList<>();
        studentGitName = "test-student";
    }

    @Test
    void testCalculateScoreWithAllDeadlinesMet() {
        TestResult result = new TestResult("2_1_1", true, 10, 10, "OK");
        LocalDate commitDate = LocalDate.of(2026, 4, 15); // До мягкого дедлайна

        GradingService.GradeBreakdown breakdown = gradingService.calculateScoreWithBreakdown(
                result, task, bonuses, studentGitName, commitDate);

        assertEquals(1.0, breakdown.basePoints);
        assertEquals(0.5, breakdown.softPoints);
        assertEquals(0.5, breakdown.hardPoints);
        assertEquals(0.0, breakdown.bonusPoints);
        assertEquals(2.0, breakdown.totalPoints);
    }

    @Test
    void testCalculateScoreWithOnlyHardDeadlineMet() {
        TestResult result = new TestResult("2_1_1", true, 10, 10, "OK");
        LocalDate commitDate = LocalDate.of(2026, 5, 5); // После мягкого, до жесткого

        GradingService.GradeBreakdown breakdown = gradingService.calculateScoreWithBreakdown(
                result, task, bonuses, studentGitName, commitDate);

        assertEquals(1.0, breakdown.basePoints);
        assertEquals(0.0, breakdown.softPoints);
        assertEquals(0.5, breakdown.hardPoints);
        assertEquals(0.0, breakdown.bonusPoints);
        assertEquals(1.5, breakdown.totalPoints);
    }

    @Test
    void testCalculateScoreWithNoDeadlinesMet() {
        TestResult result = new TestResult("2_1_1", true, 10, 10, "OK");
        LocalDate commitDate = LocalDate.of(2026, 5, 15); // После жесткого дедлайна

        GradingService.GradeBreakdown breakdown = gradingService.calculateScoreWithBreakdown(
                result, task, bonuses, studentGitName, commitDate);

        assertEquals(1.0, breakdown.basePoints);
        assertEquals(0.0, breakdown.softPoints);
        assertEquals(0.0, breakdown.hardPoints);
        assertEquals(0.0, breakdown.bonusPoints);
        assertEquals(1.0, breakdown.totalPoints);
    }

    @Test
    void testCalculateScoreWithBonuses() {
        TestResult result = new TestResult("2_1_1", true, 10, 10, "OK");
        LocalDate commitDate = LocalDate.of(2026, 4, 15);

        bonuses.add(new Bonus("test-student", "2_1_1", 2.0));
        bonuses.add(new Bonus("test-student", "2_1_1", 1.5));

        GradingService.GradeBreakdown breakdown = gradingService.calculateScoreWithBreakdown(
                result, task, bonuses, studentGitName, commitDate);

        assertEquals(1.0, breakdown.basePoints);
        assertEquals(0.5, breakdown.softPoints);
        assertEquals(0.5, breakdown.hardPoints);
        assertEquals(3.5, breakdown.bonusPoints);
        assertEquals(5.5, breakdown.totalPoints);
    }

    @Test
    void testCalculateScoreWithBonusesForDifferentStudent() {
        TestResult result = new TestResult("2_1_1", true, 10, 10, "OK");
        LocalDate commitDate = LocalDate.of(2026, 4, 15);

        bonuses.add(new Bonus("other-student", "2_1_1", 2.0));

        GradingService.GradeBreakdown breakdown = gradingService.calculateScoreWithBreakdown(
                result, task, bonuses, studentGitName, commitDate);

        assertEquals(0.0, breakdown.bonusPoints);
        assertEquals(2.0, breakdown.totalPoints);
    }

    @Test
    void testCalculateScoreWithBonusesForDifferentTask() {
        TestResult result = new TestResult("2_1_1", true, 10, 10, "OK");
        LocalDate commitDate = LocalDate.of(2026, 4, 15);

        bonuses.add(new Bonus("test-student", "other-task", 2.0));

        GradingService.GradeBreakdown breakdown = gradingService.calculateScoreWithBreakdown(
                result, task, bonuses, studentGitName, commitDate);

        assertEquals(0.0, breakdown.bonusPoints);
        assertEquals(2.0, breakdown.totalPoints);
    }

    @Test
    void testCalculateScoreWithCompileError() {
        TestResult result = new TestResult("2_1_1", false, 0, 0, "COMPILE_ERROR");
        LocalDate commitDate = LocalDate.of(2026, 4, 15);

        GradingService.GradeBreakdown breakdown = gradingService.calculateScoreWithBreakdown(
                result, task, bonuses, studentGitName, commitDate);

        assertEquals(0.0, breakdown.basePoints);
        assertEquals(0.0, breakdown.softPoints);
        assertEquals(0.0, breakdown.hardPoints);
        assertEquals(0.0, breakdown.bonusPoints);
        assertEquals(0.0, breakdown.totalPoints);
    }

    @Test
    void testCalculateScoreWithJavadocError() {
        TestResult result = new TestResult("2_1_1", true, 0, 0, "JAVADOC_ERROR");
        LocalDate commitDate = LocalDate.of(2026, 4, 15);

        GradingService.GradeBreakdown breakdown = gradingService.calculateScoreWithBreakdown(
                result, task, bonuses, studentGitName, commitDate);

        assertEquals(0.0, breakdown.totalPoints);
    }

    @Test
    void testCalculateScoreWithTestError() {
        TestResult result = new TestResult("2_1_1", true, 5, 10, "TEST_ERROR");
        LocalDate commitDate = LocalDate.of(2026, 4, 15);

        GradingService.GradeBreakdown breakdown = gradingService.calculateScoreWithBreakdown(
                result, task, bonuses, studentGitName, commitDate);

        assertEquals(0.0, breakdown.totalPoints);
    }

    @Test
    void testCalculateScoreWithNullCommitDate() {
        TestResult result = new TestResult("2_1_1", true, 10, 10, "OK");

        GradingService.GradeBreakdown breakdown = gradingService.calculateScoreWithBreakdown(
                result, task, bonuses, studentGitName, null);

        assertEquals(0.0, breakdown.totalPoints);
    }

    @Test
    void testCalculateScoreWithEmptyBonusesList() {
        TestResult result = new TestResult("2_1_1", true, 10, 10, "OK");
        LocalDate commitDate = LocalDate.of(2026, 4, 15);
        List<Bonus> emptyBonuses = new ArrayList<>();

        GradingService.GradeBreakdown breakdown = gradingService.calculateScoreWithBreakdown(
                result, task, emptyBonuses, studentGitName, commitDate);

        assertEquals(0.0, breakdown.bonusPoints);
        assertEquals(2.0, breakdown.totalPoints);
    }

    @Test
    void testCalculateScoreLegacyMethod() {
        TestResult result = new TestResult("2_1_1", true, 10, 10, "OK");
        LocalDate commitDate = LocalDate.of(2026, 4, 15);

        double score = gradingService.calculateScore(
                result, task, bonuses, studentGitName, commitDate);

        assertEquals(2.0, score);
    }

    @Test
    void testCalculateScoreOnDeadlineDay() {
        TestResult result = new TestResult("2_1_1", true, 10, 10, "OK");
        LocalDate commitDate = LocalDate.of(2026, 5, 1); // Точно в мягкий дедлайн

        GradingService.GradeBreakdown breakdown = gradingService.calculateScoreWithBreakdown(
                result, task, bonuses, studentGitName, commitDate);

        assertEquals(0.5, breakdown.softPoints);
        assertEquals(2.0, breakdown.totalPoints);
    }
}
