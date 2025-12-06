package ru.nsu.g.a.vybortseva.equations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.g.a.vybortseva.equations.exceptions.DivisionByZeroException;

class ExpressionSimplifyTest {

    @Test
    void testNumberSimplify() {
        Number num = new Number(5);
        Expression simplified = num.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(5, ((Number) simplified).evaluate(""));
        assertEquals(num, simplified);
    }

    @Test
    void testVariableSimplify() {
        Variable var = new Variable("x");
        Expression simplified = var.simplify();
        assertTrue(simplified instanceof Variable);
        assertEquals("x", simplified.toString());
        assertEquals(var, simplified);
    }

    @Test
    void testAddSimplifyBothNumbers() {
        Expression add = new Add(new Number(3), new Number(2));
        Expression simplified = add.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(5, ((Number) simplified).evaluate(""));
    }

    @Test
    void testAddSimplifyWithZero() {
        Expression add1 = new Add(new Number(0), new Variable("x"));
        Expression simplified1 = add1.simplify();
        assertEquals("x", simplified1.toString());

        Expression add2 = new Add(new Variable("x"), new Number(0));
        Expression simplified2 = add2.simplify();
        assertEquals("x", simplified2.toString());
    }

    @Test
    void testSubSimplifyBothNumbers() {
        Expression sub = new Sub(new Number(10), new Number(3));
        Expression simplified = sub.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(7, ((Number) simplified).evaluate(""));
    }

    @Test
    void testSubSimplifySameExpressions() {
        Variable var = new Variable("x");
        Expression sub = new Sub(var, var);
        Expression simplified = sub.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).evaluate(""));
    }

    @Test
    void testSubSimplifyWithZero() {
        // Вычитание нуля
        Expression sub = new Sub(new Variable("x"), new Number(0));
        Expression simplified = sub.simplify();
        assertEquals("x", simplified.toString());
    }

    @Test
    void testMulSimplifyBothNumbers() {
        Expression mul = new Mul(new Number(5), new Number(3));
        Expression simplified = mul.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(15, ((Number) simplified).evaluate(""));
    }

    @Test
    void testMulSimplifyWithZero() {
        Expression mul1 = new Mul(new Number(0), new Variable("x"));
        Expression simplified1 = mul1.simplify();
        assertTrue(simplified1 instanceof Number);
        assertEquals(0, ((Number) simplified1).evaluate(""));

        Expression mul2 = new Mul(new Variable("x"), new Number(0));
        Expression simplified2 = mul2.simplify();
        assertTrue(simplified2 instanceof Number);
        assertEquals(0, ((Number) simplified2).evaluate(""));
    }

    @Test
    void testMulSimplifyWithOne() {
        Expression mul1 = new Mul(new Number(1), new Variable("x"));
        Expression simplified1 = mul1.simplify();
        assertEquals("x", simplified1.toString());

        Expression mul2 = new Mul(new Variable("x"), new Number(1));
        Expression simplified2 = mul2.simplify();
        assertEquals("x", simplified2.toString());
    }

    @Test
    void testDivSimplifyBothNumbers() {
        Expression div = new Div(new Number(10), new Number(2));
        Expression simplified = div.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(5, ((Number) simplified).evaluate(""));
    }

    @Test
    void testDivSimplifyWithZeroNumerator() {
        Expression div = new Div(new Number(0), new Variable("x"));
        Expression simplified = div.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).evaluate(""));
    }

    @Test
    void testDivSimplifyWithOneDenominator() {
        Expression div = new Div(new Variable("x"), new Number(1));
        Expression simplified = div.simplify();
        assertEquals("x", simplified.toString());
    }

    @Test
    void testComplexSimplify() {
        // (3 + 2) * 0
        Expression complex = new Mul(
                new Add(new Number(3), new Number(2)),
                new Number(0)
        );
        Expression simplified = complex.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).evaluate(""));
    }

    @Test
    void testNestedSimplify() {
        // (x - x) * 5
        Variable var = new Variable("x");
        Expression nested = new Mul(
                new Sub(var, var),
                new Number(5)
        );
        Expression simplified = nested.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).evaluate(""));
    }

    @Test
    void testSimplifyPreservesOriginal() {
        Expression original = new Add(new Number(3), new Number(2));
        Expression simplified = original.simplify();

        assertEquals("(3+2)", original.toString());
        assertEquals("5", simplified.toString());
    }

    @Test
    void testSimplifyDerivative() {
        Expression expr = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        Expression derivative = expr.derivative("x");
        Expression simplified = derivative.simplify();

        assertEquals("(0+((0*x)+(2*1)))", derivative.toString());
        assertEquals("2", simplified.toString());
    }

    @Test
    void testSimplifyWithMultipleLevels() {
        Expression expr = new Mul(
                new Add(new Number(1), new Number(0)),
                new Sub(new Variable("x"), new Variable("x"))
        );
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).evaluate(""));
    }

    @Test
    void testSimplifyDivisionByZero() {
        Expression div = new Div(new Number(5), new Number(0));
        assertThrows(DivisionByZeroException.class, () -> div.simplify());
    }

    @Test
    void testSimplifyLargeNumbers() {
        Expression add = new Add(new Number(1000000), new Number(2000000));
        Expression simplified = add.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(3000000, ((Number) simplified).evaluate(""));
    }

    @Test
    void testSimplifyNegativeNumbers() {
        Expression mul = new Mul(new Number(-5), new Number(3));
        Expression simplified = mul.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(-15, ((Number) simplified).evaluate(""));
    }

    @Test
    void testSimplifyChainOperations() {
        Expression chain = new Add(
                new Mul(new Number(2), new Number(3)),
                new Sub(new Number(10), new Number(4))
        );
        Expression simplified = chain.simplify();
        assertEquals("12", simplified.toString());
    }

    @Test
    void testSimplifyWithUnaryMinus() {
        Expression parsed = Expression.parseNoParentheses("-5 + 3");
        Expression simplified = parsed.simplify();
        assertEquals("-2", simplified.toString());
    }

    @Test
    void testSimplifyIdentity() {
        Variable var = new Variable("x");
        Expression simplified = var.simplify();
        assertTrue(var == simplified);

        Number num = new Number(42);
        Expression simplifiedNum = num.simplify();
        assertTrue(num == simplifiedNum);
    }
}