package ru.nsu.g.a.vybortseva.model;

/**
 * Represents additional points awarded to a specific student for a specific task.
 */
public class Bonus {
    private final String studentGitName;
    private final String taskId;
    private final double points;

    /**
     * Constructs a Bonus record.
     */
    public Bonus(String studentGitName, String taskId, double points) {
        this.studentGitName = studentGitName;
        this.taskId = taskId;
        this.points = points;
    }

    /**
     * Return the target student's Git username.
     */
    public String getStudentGitName() {
        return studentGitName;
    }

    /**
     * Return the related task ID.
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Return the amount of bonus points.
     */
    public double getPoints() {
        return points;
    }
}
