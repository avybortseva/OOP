package ru.nsu.g.a.vybortseva.model;

public class Bonus {
    private final String studentGitName;
    private final String taskId;
    private final int points;


    public Bonus(String studentGitName, String taskId, int points) {
        this.studentGitName = studentGitName;
        this.taskId = taskId;
        this.points = points;
    }

    public String getStudentGitName() {
        return studentGitName;
    }

    public String getTaskId() {
        return taskId;
    }

    public int getPoints() {
        return points;
    }
}
