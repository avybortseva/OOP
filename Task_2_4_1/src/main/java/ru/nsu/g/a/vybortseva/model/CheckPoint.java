package ru.nsu.g.a.vybortseva.model;

import java.time.LocalDate;

/**
 * Represents a course milestone or deadline.
 */
public class CheckPoint {
    private final String name;
    private final LocalDate date;

    /**
     * Constructs a CheckPoint with a name and a date string.
     */
    public CheckPoint(String name, String date) {
        this.name = name;
        this.date = LocalDate.parse(date);
    }

    /**
     * Return the name of the checkpoint.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the deadline date.
     */
    public LocalDate getDate() {
        return date;
    }
}
