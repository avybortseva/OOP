package ru.nsu.g.a.vybortseva.model;

import java.time.LocalDate;

public class CheckPoint {
    private final String name;
    private final LocalDate date;

    public CheckPoint(String name, String date) {
        this.name = name;
        this.date = LocalDate.parse(date);
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }
}
