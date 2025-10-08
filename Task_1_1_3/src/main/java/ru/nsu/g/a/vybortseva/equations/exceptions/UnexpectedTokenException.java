package ru.nsu.g.a.vybortseva.equations.exceptions;

/**
 * Unexpected token or symbol.
 */
public class UnexpectedTokenException extends ParseException {
    private final int position;

    /**
     * Constructs a new UnexpectedTokenException with error message and position.
     */
    public UnexpectedTokenException(String message, int position) {
        super(message + " at position " + position);
        this.position = position;
    }

    /**
     * Returns the character position where the unexpected token occurred.
     */
    public int getPosition() {
        return position;
    }
}
