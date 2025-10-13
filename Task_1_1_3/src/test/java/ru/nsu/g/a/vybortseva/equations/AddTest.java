package ru.nsu.g.a.vybortseva.equations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AddTest {

    @Test
    void testEvaluateWithNumbers() {
        Expression add = new Add(new Number(5), new Number(3));
        assertEquals(8, add.evaluate(""));
    }

    @Test
    void testEvaluateWithVariables() {
        Expression add = new Add(new Variable("x"), new Number(2));
        assertEquals(12, add.evaluate("x=10"));
    }

    @Test
    void testEvaluateWithMultipleVariables() {
        Expression add = new Add(new Variable("x"), new Variable("y"));
        assertEquals(30, add.evaluate("x=10; y=20"));
    }

    @Test
    void testEvaluateWithNegativeNumbers() {
        Expression add = new Add(new Number(-5), new Number(3));
        assertEquals(-2, add.evaluate(""));
    }

    @Test
    void testEvaluateWithZero() {
        Expression add = new Add(new Number(0), new Number(7));
        assertEquals(7, add.evaluate(""));
    }

    @Test
    void testDerivativeSameVariable() {
        Expression add = new Add(new Variable("x"), new Number(5));
        Expression derivative = add.derivative("x");

        assertEquals(1, derivative.evaluate("x=10"));
    }

    @Test
    void testDerivativeDifferentVariable() {
        Expression add = new Add(new Variable("x"), new Variable("y"));
        Expression derivative = add.derivative("x");

        assertEquals(1, derivative.evaluate("x=5; y=10"));
    }

    @Test
    void testDerivativeOfConstants() {
        Expression add = new Add(new Number(5), new Number(3));
        Expression derivative = add.derivative("x");

        assertEquals(0, derivative.evaluate(""));
    }

    @Test
    void testToStringWithNumbers() {
        Expression add = new Add(new Number(5), new Number(3));
        assertEquals("(5+3)", add.toString());
    }

    @Test
    void testToStringWithVariables() {
        Expression add = new Add(new Variable("x"), new Variable("y"));
        assertEquals("(x+y)", add.toString());
    }

    @Test
    void testToStringWithMixed() {
        Expression add = new Add(new Number(5), new Variable("x"));
        assertEquals("(5+x)", add.toString());
    }

    @Test
    void testToStringComplexExpression() {
        Expression innerAdd = new Add(new Variable("x"), new Number(1));
        Expression outerAdd = new Add(innerAdd, new Variable("y"));
        assertEquals("((x+1)+y)", outerAdd.toString());
    }

    @Test
    void testOperate() {
        Add add = new Add(new Number(1), new Number(1));

        assertEquals(15, add.operate(10, 5));
        assertEquals(0, add.operate(0, 0));
        assertEquals(-3, add.operate(-5, 2));
        assertEquals(7, add.operate(10, -3));
    }

    @Test
    void testDifferentiate() {
        Add add = new Add(new Number(1), new Number(1));

        Expression leftDeriv = new Number(2);
        Expression rightDeriv = new Number(3);

        Expression result = add.differentiate(leftDeriv, rightDeriv);

        assertEquals(5, result.evaluate(""));
    }

    @Test
    void testPrint() {
        Add add = new Add(new Number(5), new Variable("x"));
        assertDoesNotThrow(add::print);
    }
}