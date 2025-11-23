package ru.nsu.g.a.vybortseva.book;

public enum Semester {
    FIRST_SEMESTER("Первый семестр", 1),
    SECOND_SEMESTER("Второй семестр", 2),
    THIRD_SEMESTER("Третий семестр", 3),
    FOURTH_SEMESTER("Четвертый семестр", 4);

    private final String description;
    private final Integer number;

    Semester(String description, Integer number) {
        this.description = description;
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNumber() {
        return number;
    }
}
