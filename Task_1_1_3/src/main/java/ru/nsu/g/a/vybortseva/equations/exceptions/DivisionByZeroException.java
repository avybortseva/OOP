package ru.nsu.g.a.vybortseva.equations.exceptions;

public class DivisionByZeroException extends MathCalculationException {
    public DivisionByZeroException() {
        super("деление на ноль");
    }
}
