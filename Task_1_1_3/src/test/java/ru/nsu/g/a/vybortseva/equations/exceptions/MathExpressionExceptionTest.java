package ru.nsu.g.a.vybortseva.equations.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MathExpressionExceptionTest {
    @Test
    void testMathExpressionExceptionCreation() {
        String errorMessage = "Mathematical expression error";

        MathExpressionException exception = new MathExpressionException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testMathExpressionExceptionInheritance() {
        MathExpressionException exception = new MathExpressionException("test");

        assertTrue(exception instanceof RuntimeException);
    }
}