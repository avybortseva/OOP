package ru.nsu.g.a.vybortseva.equations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ParseNoParenthesesTest {

    @Test
    void testParseSimpleAddition() {
        Expression expr = Expression.parseNoParentheses("3 + 2");
        assertTrue(expr instanceof Add);
        assertEquals(5, expr.evaluate(""));
    }

    @Test
    void testParseSimpleMultiplication() {
        Expression expr = Expression.parseNoParentheses("3 * 2");
        assertTrue(expr instanceof Mul);
        assertEquals(6, expr.evaluate(""));
    }

    @Test
    void testParseWithVariables() {
        Expression expr = Expression.parseNoParentheses("x + 2");
        assertTrue(expr instanceof Add);
        assertEquals(12, expr.evaluate("x = 10"));
    }

    @Test
    void testParseOperatorPrecedence() {
        Expression expr = Expression.parseNoParentheses("3 + 2 * x");
        // Должно быть прочитано как 3 + (2 * x), а не (3 + 2) * x
        assertEquals(23, expr.evaluate("x = 10"));
    }

    @Test
    void testParseWithParentheses() {
        Expression expr = Expression.parseNoParentheses("(3 + 2) * x");
        assertEquals(50, expr.evaluate("x = 10"));
    }

    @Test
    void testParseComplexExpression() {
        Expression expr = Expression.parseNoParentheses("a + b * c - d / e");
        assertTrue(expr instanceof Sub);
        assertEquals(11, expr.evaluate("a = 10; b = 2; c = 3; d = 20; e = 4"));
    }

    @Test
    void testParseMultiLetterVariables() {
        Expression expr = Expression.parseNoParentheses("var1 * 2 + var2");
        assertEquals(13, expr.evaluate("var1 = 5; var2 = 3"));
    }

    @Test
    void testParseWithSpaces() {
        Expression expr1 = Expression.parseNoParentheses("3+2");
        Expression expr2 = Expression.parseNoParentheses("3 + 2");
        Expression expr3 = Expression.parseNoParentheses("  3  +  2  ");

        assertEquals(5, expr1.evaluate(""));
        assertEquals(5, expr2.evaluate(""));
        assertEquals(5, expr3.evaluate(""));
    }

    @Test
    void testParseUnaryMinus() {
        Expression expr1 = Expression.parseNoParentheses("-5");
        assertEquals(-5, expr1.evaluate(""));

        Expression expr2 = Expression.parseNoParentheses("-x");
        assertEquals(-10, expr2.evaluate("x = 10"));

        Expression expr3 = Expression.parseNoParentheses("3 * -x");
        assertEquals(-30, expr3.evaluate("x = 10"));
    }

    @Test
    void testParseNestedUnaryMinus() {
        Expression expr = Expression.parseNoParentheses("-(-x)");
        assertEquals(7, expr.evaluate("x = 7"));
    }

    @Test
    void testParseInvalidExpression() {
        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parseNoParentheses("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parseNoParentheses("3 + ");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parseNoParentheses("(3 + 2");
        });
    }

    @Test
    void testParseDivision() {
        Expression expr = Expression.parseNoParentheses("10 / 2");
        assertTrue(expr instanceof Div);
        assertEquals(5, expr.evaluate(""));
    }

    @Test
    void testParseSubtraction() {
        Expression expr = Expression.parseNoParentheses("10 - 3");
        assertTrue(expr instanceof Sub);
        assertEquals(7, expr.evaluate(""));
    }

    @Test
    void testParseMixedOperators() {
        Expression expr = Expression.parseNoParentheses("1 + 2 * 3 - 4 / 2");
        assertEquals(5, expr.evaluate(""));
    }

    @Test
    void testParseWithMultipleParentheses() {
        Expression expr = Expression.parseNoParentheses("((a + b) * (c - d)) / 2");
        assertEquals(32, expr.evaluate("a = 3; b = 5; c = 10; d = 2"));
    }

    @Test
    void testParseNegativeNumbers() {
        Expression expr = Expression.parseNoParentheses("-3 + -5");
        assertEquals(-8, expr.evaluate(""));
    }

    @Test
    void testParseComplexExpressionWithVariables() {
        Expression expr = Expression.parseNoParentheses("(x + y) * 2 - z / 3");
        assertEquals(13, expr.evaluate("x = 5; y = 3; z = 9"));
    }

    @Test
    void testParseSingleNumber() {
        Expression expr = Expression.parseNoParentheses("42");
        assertTrue(expr instanceof Number);
        assertEquals(42, expr.evaluate(""));
    }

    @Test
    void testParseSingleVariable() {
        Expression expr = Expression.parseNoParentheses("x");
        assertTrue(expr instanceof Variable);
        assertEquals(10, expr.evaluate("x = 10"));
    }

    @Test
    void testParseLargeExpression() {
        // Большое выражение для проверки производительности
        Expression expr = Expression.parseNoParentheses(
                "a + b * c - d / e + f * g - h / i + j * k"
        );
        // Проверяем, что парсинг работает без ошибок
        assertTrue(expr instanceof Add || expr instanceof Sub);
    }

    @Test
    void testParseWithUnderscoreVariables() {
        Expression expr = Expression.parseNoParentheses("my_var_1 + temp_2");
        assertEquals(15, expr.evaluate("my_var_1 = 10; temp_2 = 5"));
    }

    @Test
    void testParseInvalidOperator() {
        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parseNoParentheses("3 & 2");
        });
    }

    @Test
    void testParseMismatchedParentheses() {
        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parseNoParentheses("(3 + 2");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parseNoParentheses("3 + 2)");
        });
    }

    @Test
    void testParseWhitespaceOnly() {
        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parseNoParentheses("   ");
        });
    }

    @Test
    void testParseEmptyParentheses() {
        assertThrows(IllegalArgumentException.class, () -> {
            Expression.parseNoParentheses("()");
        });
    }

    @Test
    void testParseUnaryMinusAtStart() {
        Expression expr = Expression.parseNoParentheses("-x + 5");
        assertEquals(0, expr.evaluate("x = 5"));
    }

    @Test
    void testParseUnaryMinusInMiddle() {
        Expression expr = Expression.parseNoParentheses("10 - -x");
        assertEquals(13, expr.evaluate("x = 3"));
    }

    @Test
    void testParseUnaryMinusWithParentheses() {
        Expression expr = Expression.parseNoParentheses("-(x + 5)");
        assertEquals(-8, expr.evaluate("x = 3"));
    }
}