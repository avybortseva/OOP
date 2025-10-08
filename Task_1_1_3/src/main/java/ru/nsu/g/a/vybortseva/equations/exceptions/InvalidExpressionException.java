package ru.nsu.g.a.vybortseva.equations.exceptions;

/**
 * Thrown when mathematical expression has invalid format or syntax.
 */
public class InvalidExpressionException extends ParseException {

    /**
     * Constructs a new InvalidExpressionException for the given expression.
     */
    public InvalidExpressionException(String expression) {
        super("Invalid expression: " + expression);
    }
}
