package ru.nsu.g.a.vybortseva.equations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NumberTest {

    @Test
    void testEvaluate() {
        Number num1 = new Number(5);
        assertEquals(5, num1.evaluate("x=10; y=20"));

        Number num2 = new Number(0);
        assertEquals(0, num2.evaluate("any string here"));

        Number num3 = new Number(-10);
        assertEquals(-10, num3.evaluate(""));

        Number num4 = new Number(42);
        assertEquals(42, num4.evaluate(null));
    }

    @Test
    void testDerivative() {
        Number num1 = new Number(5);
        assertEquals(0, num1.derivative("x=10; y=20").evaluate(""));

        Number num2 = new Number(0);
        assertEquals(0, num2.derivative("any string here").evaluate(""));

        Number num3 = new Number(-10);
        assertEquals(0, num3.derivative("").evaluate(""));

        Number num4 = new Number(42);
        assertEquals(0, num4.derivative(null).evaluate(""));
    }

    @Test
    void testToString() {
        Number num1 = new Number(5);
        assertEquals("5", num1.toString());

        Number num2 = new Number(0);
        assertEquals("0", num2.toString());

        Number num3 = new Number(-10);
        assertEquals("-10", num3.toString());

        Number num4 = new Number(123456789);
        assertEquals("123456789", num4.toString());
    }

    @Test
    void testPrint() {
        Number num1 = new Number(5);
        Number num2 = new Number(0);
        Number num3 = new Number(-10);

        // Проверяем что исключения не кидает
        assertDoesNotThrow(num1::print);
        assertDoesNotThrow(num2::print);
        assertDoesNotThrow(num3::print);
    }
}