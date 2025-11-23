package ru.nsu.g.a.vybortseva.book;

public enum Grade {
    EXCELLENT("отлично", 5),
    GOOD("хорошо", 4),
    SATISFACTORY("удовлетворительно", 3),
    FAIL("неудовлетворительно", 2),
    UNDEFINED("неопределенная", -1);

    private final String description;
    private final int numericValue;

    Grade(String description, int numericValue) {
        this.description = description;
        this.numericValue = numericValue;
    }

    public String getDescription() {
        return description;
    }
    public int getNumericValue() {
        return numericValue;
    }
}
