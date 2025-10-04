package ru.nsu.g.a.vybortseva.equations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MulTest {

    @Test
    void testEvaluateWithNumbers() {
        Expression mul = new Mul(new Number(5), new Number(3));
        assertEquals(15, mul.evaluate(""));
    }

    @Test
    void testEvaluateWithVariables() {
        Expression mul = new Mul(new Variable("x"), new Number(2));
        assertEquals(20, mul.evaluate("x=10"));
    }

    @Test
    void testEvaluateWithMultipleVariables() {
        Expression mul = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(20, mul.evaluate("x=5; y=4"));
    }

    @Test
    void testEvaluateWithNegativeNumbers() {
        Expression mul = new Mul(new Number(-5), new Number(3));
        assertEquals(-15, mul.evaluate(""));
    }

    @Test
    void testEvaluateWithZero() {
        Expression mul = new Mul(new Number(0), new Number(7));
        assertEquals(0, mul.evaluate(""));
    }

    @Test
    void testEvaluateNegativeByNegative() {
        Expression mul = new Mul(new Number(-5), new Number(-3));
        assertEquals(15, mul.evaluate(""));
    }

    @Test
    void testDerivativeSameVariable() {
        Expression mul = new Mul(new Variable("x"), new Number(5));
        Expression derivative = mul.derivative("x");

        assertEquals(5, derivative.evaluate("x=10")); // 5 не зависит от x
    }

    @Test
    void testDerivativeDifferentVariables() {
        Expression mul = new Mul(new Variable("x"), new Variable("y"));
        Expression derivative = mul.derivative("x");

        assertEquals(8, derivative.evaluate("x=3; y=8")); // Должно быть равно y = 8
    }

    @Test
    void testDerivativeOfConstants() {
        Expression mul = new Mul(new Number(5), new Number(3));
        Expression derivative = mul.derivative("x");

        assertEquals(0, derivative.evaluate(""));
    }

    @Test
    void testToStringWithNumbers() {
        Expression mul = new Mul(new Number(5), new Number(3));
        assertEquals("(5*3)", mul.toString());
    }

    @Test
    void testToStringWithVariables() {
        Expression mul = new Mul(new Variable("x"), new Variable("y"));
        assertEquals("(x*y)", mul.toString());
    }

    @Test
    void testToStringWithMixed() {
        Expression mul = new Mul(new Number(5), new Variable("x"));
        assertEquals("(5*x)", mul.toString());
    }

    @Test
    void testToStringComplexExpression() {
        Expression innerMul = new Mul(new Variable("x"), new Number(2));
        Expression outerMul = new Mul(innerMul, new Variable("y"));
        assertEquals("((x*2)*y)", outerMul.toString());
    }

    @Test
    void testOperate() {
        Mul mul = new Mul(new Number(1), new Number(1));

        assertEquals(50, mul.operate(10, 5));
        assertEquals(0, mul.operate(0, 0));
        assertEquals(0, mul.operate(0, 5));
        assertEquals(0, mul.operate(5, 0));
        assertEquals(-10, mul.operate(-5, 2));
        assertEquals(-15, mul.operate(5, -3));
        assertEquals(6, mul.operate(-2, -3));
    }

    @Test
    void testDifferentiate() {
        Mul mul = new Mul(new Number(1), new Number(1));

        Expression leftDeriv = new Number(3);
        Expression rightDeriv = new Number(2);

        Expression left = new Number(5);
        Expression right = new Number(4);

        Mul mulWithOperands = new Mul(left, right);
        Expression result = mulWithOperands.differentiate(leftDeriv, rightDeriv);

        assertEquals(22, result.evaluate(""));
    }

    @Test
    void testPrint() {
        Mul mul = new Mul(new Number(3), new Variable("z"));
        assertDoesNotThrow(mul::print);
    }
}