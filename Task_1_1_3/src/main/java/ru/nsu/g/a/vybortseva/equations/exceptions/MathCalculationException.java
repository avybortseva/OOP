package ru.nsu.g.a.vybortseva.equations.exceptions;

/**
 * Thrown when mathematical calculation fails due to domain-specific errors.
 */
public class MathCalculationException extends CalculationException{

    /**
     * Constructs a new MathCalculationException with a prefixed mathematical error message.
     */
    public MathCalculationException(String message) {
        super("Математическая ошибка: " + message);
    }
}
