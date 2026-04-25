package ru.nsu.g.a.vybortseva.model;

import java.time.LocalDate;

public class Task {
    private final String id;
    private final String title;
    private final int maxPoints;
    private final LocalDate softDeadline;
    private final LocalDate hardDeadline;


    public Task(String id, String title, int maxPoints, String soft, String hard) {
        this.id = id;
        this.title = title;
        this.maxPoints = maxPoints;
        this.softDeadline = LocalDate.parse(soft);
        this.hardDeadline = LocalDate.parse(hard);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public LocalDate getSoftDeadline() {
        return softDeadline;
    }

    public LocalDate getHardDeadline() {
        return hardDeadline;
    }
}
