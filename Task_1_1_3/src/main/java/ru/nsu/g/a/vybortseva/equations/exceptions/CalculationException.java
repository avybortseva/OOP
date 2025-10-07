package ru.nsu.g.a.vybortseva.equations.exceptions;

/**
 * Exception thrown when calculation fails in mathematical expressions.
 */
public class CalculationException extends MathExpressionException{

    /**
     * Constructs a new CalculationException with the specified detail message.
     */
    public CalculationException(String message) {
        super(message);
    }
}
