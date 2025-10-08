package ru.nsu.g.a.vybortseva.equations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.nsu.g.a.vybortseva.equations.exceptions.UnexpectedTokenException;

class ParserTest {
    @Test
    void testParseNumber() {
        Parser parser = new Parser();
        Expression result = parser.parse("5");
        assertEquals(5, result.evaluate(""));
    }

    @Test
    void testParseVariable() {
        Parser parser = new Parser();
        Expression result = parser.parse("x");
        assertEquals(10, result.evaluate("x=10"));
    }

    @Test
    void testParseAdd() {
        Parser parser = new Parser();
        Expression result = parser.parse("(x+5)");
        assertEquals(15, result.evaluate("x=10"));
    }

    @Test
    void testParseSub() {
        Parser parser = new Parser();
        Expression result = parser.parse("(x-3)");
        assertEquals(7, result.evaluate("x=10"));
    }

    @Test
    void testParseMul() {
        Parser parser = new Parser();
        Expression result = parser.parse("(x*2)");
        assertEquals(20, result.evaluate("x=10"));
    }

    @Test
    void testParseDiv() {
        Parser parser = new Parser();
        Expression result = parser.parse("(x/2)");
        assertEquals(5, result.evaluate("x=10"));
    }

    @Test
    void testParseComplexExpression() {
        Parser parser = new Parser();
        Expression result = parser.parse("((x+1)*2)");
        assertEquals(22, result.evaluate("x=10"));
    }

    @Test
    void testParseWithSpaces() {
        Parser parser = new Parser();
        Expression result = parser.parse(" ( x + 5 ) ");
        assertEquals(15, result.evaluate("x=10"));
    }

    @Test
    void testParseMultiLetterVariable() {
        Parser parser = new Parser();
        Expression result = parser.parse("var");
        assertEquals(42, result.evaluate("var=42"));
    }

    @Test
    void testParseNestedExpressions() {
        Parser parser = new Parser();
        Expression result = parser.parse("((x*y)+(a/b))");
        assertEquals(23, result.evaluate("x=5;y=4;a=6;b=2"));
    }

    @Test
    void testParseUnexpectedEnd() {
        Parser parser = new Parser();
        assertThrows(UnexpectedTokenException.class, () -> parser.parse("(x+"));
    }

    @Test
    void testParseEmptyToken() {
        Parser parser = new Parser();
        assertThrows(UnexpectedTokenException.class, () -> parser.parse("()"));
    }

    @Test
    void testParseMultipleDigits() {
        Parser parser = new Parser();
        Expression result = parser.parse("123");
        assertEquals(123, result.evaluate(""));
    }

    @Test
    void testParseDeeplyNested() {
        Parser parser = new Parser();
        Expression result = parser.parse("(((x+1)*2)-3)");
        assertEquals(19, result.evaluate("x=10"));
    }

    @Test
    void testParseMixedCaseVariable() {
        Parser parser = new Parser();
        Expression result = parser.parse("MyVar");
        assertEquals(100, result.evaluate("MyVar=100"));
    }

    @Test
    void testParseExpressionWithAllOperators() {
        Parser parser = new Parser();
        Expression result = parser.parse("(((a+b)*(c-d))/e)");
        assertEquals(10, result.evaluate("a=2;b=3;c=8;d=2;e=3"));
    }
}