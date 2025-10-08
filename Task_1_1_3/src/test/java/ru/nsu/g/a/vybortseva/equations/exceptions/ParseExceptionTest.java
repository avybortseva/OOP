package ru.nsu.g.a.vybortseva.equations.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ParseExceptionTest {
    @Test
    void testParseExceptionCreation() {
        String errorMessage = "Parsing failed";
        ParseException exception = new ParseException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testParseExceptionInheritance() {
        ParseException exception = new ParseException("test");

        assertTrue(exception instanceof MathExpressionException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testUnexpectedTokenExceptionCreation() {
        String message = "Unexpected token";
        int position = 5;

        UnexpectedTokenException exception = new UnexpectedTokenException(message, position);

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains(message));
        assertTrue(exception.getMessage().contains("position"));
        assertEquals(position, exception.getPosition());
    }

    @Test
    void testUnexpectedTokenExceptionInheritance() {
        UnexpectedTokenException exception = new UnexpectedTokenException("test", 0);

        assertTrue(exception instanceof ParseException);
        assertTrue(exception instanceof MathExpressionException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testInvalidExpressionExceptionCreation() {
        String expression = "invalid expr";

        InvalidExpressionException exception = new InvalidExpressionException(expression);

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains(expression));
    }

    @Test
    void testInvalidExpressionExceptionInheritance() {
        InvalidExpressionException exception = new InvalidExpressionException("test");

        assertTrue(exception instanceof ParseException);
        assertTrue(exception instanceof MathExpressionException);
        assertTrue(exception instanceof RuntimeException);
    }

}