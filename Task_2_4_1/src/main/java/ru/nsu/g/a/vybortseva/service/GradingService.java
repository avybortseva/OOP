package ru.nsu.g.a.vybortseva.service;

import java.time.LocalDate;
import java.util.List;
import ru.nsu.g.a.vybortseva.model.Bonus;
import ru.nsu.g.a.vybortseva.model.Student;
import ru.nsu.g.a.vybortseva.model.Task;
import ru.nsu.g.a.vybortseva.model.TestResult;

/**
 * Service for grading tasks.
 */
public class GradingService {

    private static final double BASE_POINTS = 1.0;           // 1 балл за сдачу в целом
    private static final double SOFT_REWARD = 0.5;           // +0.5 за мягкий дедлайн
    private static final double HARD_REWARD = 0.5;           // +0.5 за жесткий дедлайн

    /**
     * Result of grading with detailed breakdown.
     */
    public static class GradeBreakdown {
        public final double basePoints;
        public final double softPoints;
        public final double hardPoints;
        public final double bonusPoints;
        public final double totalPoints;

        /**
         * Constructor for grade breakdown.
         */
        public GradeBreakdown(double basePoints, double softPoints,
                              double hardPoints, double bonusPoints) {
            this.basePoints = basePoints;
            this.softPoints = softPoints;
            this.hardPoints = hardPoints;
            this.bonusPoints = bonusPoints;
            this.totalPoints = basePoints + softPoints + hardPoints + bonusPoints;
        }
    }

    /**
     * Calculate score and return full breakdown.
     */
    public GradeBreakdown calculateScoreWithBreakdown(TestResult result, Task task,
                                                      List<Bonus> allBonuses,
                                                      String studentGitName,
                                                      LocalDate commitDate) {
        if (!"OK".equals(result.getStatus()) || commitDate == null) {
            return new GradeBreakdown(0, 0, 0, 0);
        }

        double soft = 0.0;
        double hard = 0.0;

        if (task.getSoftDeadline() != null && !commitDate.isAfter(task.getSoftDeadline())) {
            soft = SOFT_REWARD;
        }
        if (task.getHardDeadline() != null && !commitDate.isAfter(task.getHardDeadline())) {
            hard = HARD_REWARD;
        }

        double bonus = allBonuses.stream()
                .filter(b -> b.getStudentGitName().equals(studentGitName))
                .filter(b -> b.getTaskId().equals(task.getId()))
                .mapToDouble(Bonus::getPoints)
                .sum();

        return new GradeBreakdown(BASE_POINTS, soft, hard, bonus);
    }

    /**
     * Method for backward compatibility.
     */
    public double calculateScore(TestResult result, Task task, List<Bonus> allBonuses,
                                 String studentGitName, LocalDate commitDate) {
        GradeBreakdown breakdown = calculateScoreWithBreakdown(result,
                task, allBonuses, studentGitName, commitDate);
        return breakdown.totalPoints;
    }

    /**
     * Method creating empty task data.
     */
    public ReportGenerator.StudentTaskData createEmptyTaskData(Student student, Task task) {
        if (task == null) {
            return new ReportGenerator.StudentTaskData(
                    student.getId(),
                    student.getFullName(),
                    "unknown",
                    "Unknown Task",
                    false, false, false,
                    0, 0,
                    null,
                    new GradeBreakdown(0, 0, 0, 0)
            );
        }

        return new ReportGenerator.StudentTaskData(
                student.getId(),
                student.getFullName(),
                task.getId(),
                task.getTitle(),
                false, false, false,
                0, 0,
                null,
                new GradeBreakdown(0, 0, 0, 0)
        );
    }
}
