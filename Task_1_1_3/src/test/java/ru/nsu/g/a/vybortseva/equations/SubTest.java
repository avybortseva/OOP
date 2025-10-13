package ru.nsu.g.a.vybortseva.equations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SubTest {
    @Test
    void testEvaluateWithNumbers() {
        Expression sub = new Sub(new Number(10), new Number(3));
        assertEquals(7, sub.evaluate(""));
    }

    @Test
    void testEvaluateWithVariables() {
        Expression sub = new Sub(new Variable("x"), new Number(2));
        assertEquals(8, sub.evaluate("x=10"));
    }

    @Test
    void testEvaluateWithMultipleVariables() {
        Expression sub = new Sub(new Variable("x"), new Variable("y"));
        assertEquals(15, sub.evaluate("x=20; y=5"));
    }

    @Test
    void testEvaluateWithNegativeNumbers() {
        Expression sub = new Sub(new Number(-5), new Number(3));
        assertEquals(-8, sub.evaluate(""));
    }

    @Test
    void testEvaluateWithZero() {
        Expression sub = new Sub(new Number(0), new Number(7));
        assertEquals(-7, sub.evaluate(""));
    }

    @Test
    void testDerivativeSameVariable() {
        Expression sub = new Sub(new Variable("x"), new Number(5));
        Expression derivative = sub.derivative("x");

        assertEquals(1, derivative.evaluate("x=10"));
    }

    @Test
    void testDerivativeDifferentVariable() {
        Expression sub = new Sub(new Variable("x"), new Variable("y"));
        Expression derivative = sub.derivative("x");

        assertEquals(1, derivative.evaluate("x=5; y=10"));
    }

    @Test
    void testDerivativeOfConstants() {
        Expression sub = new Sub(new Number(10), new Number(3));
        Expression derivative = sub.derivative("x");

        assertEquals(0, derivative.evaluate(""));
    }

    @Test
    void testToStringWithNumbers() {
        Expression sub = new Sub(new Number(10), new Number(3));
        assertEquals("(10-3)", sub.toString());
    }

    @Test
    void testToStringWithVariables() {
        Expression sub = new Sub(new Variable("x"), new Variable("y"));
        assertEquals("(x-y)", sub.toString());
    }

    @Test
    void testToStringWithMixed() {
        Expression sub = new Sub(new Number(10), new Variable("x"));
        assertEquals("(10-x)", sub.toString());
    }

    @Test
    void testToStringComplexExpression() {
        Expression innerSub = new Sub(new Variable("x"), new Number(1));
        Expression outerSub = new Sub(innerSub, new Variable("y"));
        assertEquals("((x-1)-y)", outerSub.toString());
    }


    @Test
    void testOperate() {
        Sub sub = new Sub(new Number(1), new Number(1));

        assertEquals(5, sub.operate(10, 5));
        assertEquals(0, sub.operate(0, 0));
        assertEquals(-7, sub.operate(-5, 2));
        assertEquals(13, sub.operate(10, -3));
        assertEquals(-2, sub.operate(3, 5));
    }

    @Test
    void testDifferentiate() {
        Sub sub = new Sub(new Number(1), new Number(1));

        Expression leftDeriv = new Number(5);
        Expression rightDeriv = new Number(2);

        Expression result = sub.differentiate(leftDeriv, rightDeriv);

        assertEquals(3, result.evaluate(""));
    }

    @Test
    void testPrint() {
        Sub sub = new Sub(new Number(10), new Variable("y"));
        assertDoesNotThrow(sub::print);
    }
}