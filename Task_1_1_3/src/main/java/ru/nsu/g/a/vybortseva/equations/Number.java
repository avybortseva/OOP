package ru.nsu.g.a.vybortseva.equations;

/**
 * The class for a number.
 */
public class Number extends Expression {
    private final int number;

    /**
     * Constructs for a number.
     */
    public Number(int number) {
        this.number = number;
    }

    // Геттер для доступа к значению
    public int getValue() {
        return number;
    }

    /**
     * Method for evaluating of a number is the number itself.
     */
    @Override
    public int evaluate(String variablesStr) {
        getCachedVariables(variablesStr);
        return number;
    }

    /**
     * Method for printing of a number.
     */
    @Override
    public void print() {
        System.out.print(number);
    }

    /**
     * Method for derivative of a number is the number itself.
     */
    @Override
    public Expression derivative(String variableStr) {
        return new Number(0);
    }

    /**
     * Упрощение числа.
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * Method toString.
     */
    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
