package ru.nsu.g.a.vybortseva.equations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DivTest {

    @Test
    void testEvaluateWithNumbers() {
        Expression div = new Div(new Number(10), new Number(2));
        assertEquals(5, div.evaluate(""));
    }

    @Test
    void testEvaluateWithVariables() {
        Expression div = new Div(new Variable("x"), new Number(2));
        assertEquals(5, div.evaluate("x=10"));
    }

    @Test
    void testEvaluateWithMultipleVariables() {
        Expression div = new Div(new Variable("x"), new Variable("y"));
        assertEquals(3, div.evaluate("x=15; y=5"));
    }

    @Test
    void testEvaluateWithNegativeNumbers() {
        Expression div = new Div(new Number(-10), new Number(2));
        assertEquals(-5, div.evaluate(""));
    }

    @Test
    void testEvaluateWithZeroNumerator() {
        Expression div = new Div(new Number(0), new Number(5));
        assertEquals(0, div.evaluate(""));
    }

    @Test
    void testEvaluateIntegerDivision() {
        Expression div = new Div(new Number(7), new Number(2));
        assertEquals(3, div.evaluate(""));
    }

    @Test
    void testDerivativeSameVariable() {
        Expression div = new Div(new Variable("x"), new Number(2));
        Expression derivative = div.derivative("x");
        assertEquals(0, derivative.evaluate("x=10"));
    }

    @Test
    void testDerivativeDifferentVariables() {
        Expression div = new Div(new Variable("x"), new Variable("y"));
        Expression derivative = div.derivative("x");
        assertEquals(0, derivative.evaluate("x=10; y=5"));
    }

    @Test
    void testDerivativeOfConstants() {
        Expression div = new Div(new Number(10), new Number(2));
        Expression derivative = div.derivative("x");
        assertEquals(0, derivative.evaluate(""));
    }

    @Test
    void testToStringWithNumbers() {
        Expression div = new Div(new Number(10), new Number(2));
        assertEquals("(10/2)", div.toString());
    }

    @Test
    void testToStringWithVariables() {
        Expression div = new Div(new Variable("x"), new Variable("y"));
        assertEquals("(x/y)", div.toString());
    }

    @Test
    void testToStringWithMixed() {
        Expression div = new Div(new Number(10), new Variable("x"));
        assertEquals("(10/x)", div.toString());
    }

    @Test
    void testToStringComplexExpression() {
        Expression innerDiv = new Div(new Variable("x"), new Number(2));
        Expression outerDiv = new Div(innerDiv, new Variable("y"));
        assertEquals("((x/2)/y)", outerDiv.toString());
    }

    @Test
    void testOperate() {
        Div div = new Div(new Number(1), new Number(1));
        assertEquals(2, div.operate(10, 5));
        assertEquals(0, div.operate(0, 5));
        assertEquals(-3, div.operate(-6, 2));
        assertEquals(-2, div.operate(10, -5));
        assertEquals(1, div.operate(-3, -3));
        assertEquals(3, div.operate(7, 2));
    }

    @Test
    void testDifferentiate() {
        Div div = new Div(new Number(1), new Number(1));
        Expression leftDeriv = new Number(3);
        Expression rightDeriv = new Number(2);
        Expression left = new Number(5);
        Expression right = new Number(4);

        Div divWithOperands = new Div(left, right);
        Expression result = divWithOperands.differentiate(leftDeriv, rightDeriv);

        assertEquals(0, result.evaluate(""));
    }

    @Test
    void testPrint() {
        Div div = new Div(new Number(15), new Variable("a"));
        assertDoesNotThrow(div::print);
    }
}