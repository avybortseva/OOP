package ru.nsu.g.a.vybortseva.equations.exceptions;

/**
 * A basic exception for all errors in the design of mathematical expressions.
 */
public class MathExpressionException extends RuntimeException {

    /**
     * Constructs a new MathExpressionException with the specified detail message.
     */
    public MathExpressionException(String message) {
        super(message);
    }
}
