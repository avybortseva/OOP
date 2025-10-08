package ru.nsu.g.a.vybortseva.equations.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MathCalculationExceptionTest {
    @Test
    void testMathCalculationExceptionCreation() {
        String errorMessage = "division error";

        MathCalculationException exception = new MathCalculationException(errorMessage);

        assertNotNull(exception);
        assertEquals("Math calculation error: " + errorMessage, exception.getMessage());
    }

    @Test
    void testMathCalculationExceptionInheritance() {
        MathCalculationException exception = new MathCalculationException("test");

        assertTrue(exception instanceof CalculationException);
        assertTrue(exception instanceof MathExpressionException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testDivisionByZeroExceptionCreation() {
        DivisionByZeroException exception = new DivisionByZeroException();

        assertNotNull(exception);
        assertEquals("Math calculation error: division by zero", exception.getMessage());
    }

    @Test
    void testDivisionByZeroExceptionInheritance() {
        DivisionByZeroException exception = new DivisionByZeroException();

        assertTrue(exception instanceof MathCalculationException);
        assertTrue(exception instanceof CalculationException);
        assertTrue(exception instanceof MathExpressionException);
        assertTrue(exception instanceof RuntimeException);
    }
}