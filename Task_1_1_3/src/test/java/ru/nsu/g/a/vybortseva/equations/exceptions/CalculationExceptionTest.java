package ru.nsu.g.a.vybortseva.equations.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CalculationExceptionTest {

    @Test
    void testCalculationExceptionCreation() {
        String errorMessage = "Calculation failed due to invalid input";
        CalculationException exception = new CalculationException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testCalculationExceptionInheritance() {
        // When
        CalculationException exception = new CalculationException("test");

        // Then
        assertTrue(exception instanceof MathExpressionException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testUndefinedVariableExceptionCreation() {
        String variableName = "undefinedVar";
        UndefinedVariableException exception = new UndefinedVariableException(variableName);

        assertNotNull(exception);
        assertEquals("Variable '" + variableName + "' is not defined", exception.getMessage());
        assertEquals(variableName, exception.getVariableName());
    }

    @Test
    void testUndefinedVariableExceptionInheritance() {
        UndefinedVariableException exception = new UndefinedVariableException("test");

        assertTrue(exception instanceof CalculationException);
        assertTrue(exception instanceof MathExpressionException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testUndefinedVariableExceptionWithDifferentNames() {
        String[] variableNames = {"x", "y", "temp", "result", "user_input"};

        for (String varName : variableNames) {
            UndefinedVariableException exception = new UndefinedVariableException(varName);
            assertEquals("Variable '" + varName + "' is not defined", exception.getMessage());
            assertEquals(varName, exception.getVariableName());
        }
    }
}