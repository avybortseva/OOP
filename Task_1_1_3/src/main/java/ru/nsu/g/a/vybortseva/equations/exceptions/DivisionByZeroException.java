package ru.nsu.g.a.vybortseva.equations.exceptions;

/**
 * Class for DivisionByZeroException.
 */
public class DivisionByZeroException extends MathCalculationException {

    /**
     * Division by zero.
     */
    public DivisionByZeroException() {
        super("division by zero");
    }
}
