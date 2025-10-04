package ru.nsu.g.a.vybortseva.equations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class VariableTest {

    @Test
    void testEvaluate() {
        Variable var1 = new Variable("x");
        assertEquals(10, var1.evaluate("x=10; y=20"));

        Variable var2 = new Variable("y");
        assertEquals(20, var2.evaluate("x= 10; y   =20"));

        Variable var3 = new Variable("x");
        assertEquals(5, var3.evaluate("x=5"));

        Variable varName = new Variable("myVar");
        assertEquals(42, varName.evaluate("myVar=42"));
    }

    @Test
    void testPrint() {
        Variable var = new Variable("x");

        assertDoesNotThrow(var::print);
    }

    @Test
    void testDerivative() {
        Variable var1 = new Variable("x");
        assertEquals(0, var1.derivative("x=10; y=20").evaluate(""));

        Variable var2 = new Variable("x");
        assertEquals(1, var2.derivative("x").evaluate(""));

        Variable var3 = new Variable("x");
        assertEquals(0, var3.derivative(" x=   0").evaluate(""));

        Variable var4 = new Variable("Var");
        assertEquals(0, var4.derivative("Var=10 ; y=20").evaluate(""));
    }

    @Test
    void testToString() {
        Variable var1 = new Variable("x");
        assertEquals("x", var1.toString());

        Variable var2 = new Variable("y");
        assertEquals("y", var2.toString());

        Variable var3 = new Variable("myVariable");
        assertEquals("myVariable", var3.toString());

        Variable var4 = new Variable("temp");
        assertEquals("temp", var4.toString());
    }
}