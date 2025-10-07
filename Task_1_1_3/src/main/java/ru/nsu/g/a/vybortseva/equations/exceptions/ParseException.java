package ru.nsu.g.a.vybortseva.equations.exceptions;

/**
 * Exception for expression parsing errors.
 */
public class ParseException extends MathExpressionException {

    /**
     * Constructs a new ParseException with the specified parsing error message.
     */
    public ParseException(String message) {
        super(message);
    }
}