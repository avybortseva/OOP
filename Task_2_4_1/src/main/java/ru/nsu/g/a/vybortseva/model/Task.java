package ru.nsu.g.a.vybortseva.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Represents a laboratory work or assignment with deadlines and grading criteria.
 */
public class Task {
    private final String id;
    private final String title;
    private final LocalDate  softDeadline;
    private final LocalDate  hardDeadline;

    /**
     * Constructs a Task with defined deadlines.
     */
    public Task(String id, String title, String soft, String hard) {
        this.id = id;
        this.title = title;
        this.softDeadline = LocalDate.parse(soft);
        this.hardDeadline = LocalDate.parse(hard);
    }

    /**
     * Return the unique task ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Return the task title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Return the date of the soft deadline.
     */
    public LocalDate getSoftDeadline() {
        return softDeadline;
    }

    /**
     * Return the date of the hard deadline.
     */
    public LocalDate  getHardDeadline() {
        return hardDeadline;
    }
}
