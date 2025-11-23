package ru.nsu.g.a.vybortseva.book;

/**
 * Represents possible grades for academic assessments.
 * Includes numeric values for grade calculations.
 */
public enum Grade {
    EXCELLENT("отлично", 5),
    GOOD("хорошо", 4),
    SATISFACTORY("удовлетворительно", 3),
    FAIL("неудовлетворительно", 2),
    UNDEFINED("неопределенная", -1);

    private final String description;
    private final int numericValue;

    /**
     * Creates a grade with its description and numeric value.
     */
    Grade(String description, int numericValue) {
        this.description = description;
        this.numericValue = numericValue;
    }

    /**
     * Returns the textual description of the grade.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the numeric value associated with the grade.
     */
    public int getNumericValue() {
        return numericValue;
    }
}
