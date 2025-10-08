package ru.nsu.g.a.vybortseva.equations.exceptions;

/**
 * Thrown when an undefined variable is encountered during expression calculation.
 */
public class UndefinedVariableException extends CalculationException{
    private final String variableName;

    /**
     * Constructs a new UndefinedVariableException for the specified variable.
     */
    public UndefinedVariableException(String variableName) {
        super("Variable '" + variableName + "' is not defined");
        this.variableName = variableName;
    }

    /**
     * Returns the name of the undefined variable that caused this exception.
     */
    public String getVariableName() {
        return variableName;
    }
}
