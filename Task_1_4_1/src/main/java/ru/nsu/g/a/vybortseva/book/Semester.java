package ru.nsu.g.a.vybortseva.book;

/**
 * Represents academic semesters in the educational process.
 * Each semester has a description and sequential number.
 */
public enum Semester {
    FIRST_SEMESTER("Первый семестр", 1),
    SECOND_SEMESTER("Второй семестр", 2),
    THIRD_SEMESTER("Третий семестр", 3),
    FOURTH_SEMESTER("Четвертый семестр", 4),
    FIFTH_SEMESTER("Пятый семестр", 5),
    SIXTH_SEMESTER("Шестой семестр", 6),
    SEVENTH_SEMESTER("Седьмой семестр", 7),
    EIGHTH_SEMESTER("Восьмой семестр", 8);

    private final String description;
    private final Integer number;

    /**
     * Creates a semester with its description and sequential number.
     */
    Semester(String description, Integer number) {
        this.description = description;
        this.number = number;
    }

    /**
     * Returns the textual description of the semester.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the sequential number of the semester.
     */
    public Integer getNumber() {
        return number;
    }
}
