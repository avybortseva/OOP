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
    void testEvaluateWithCache() {
        String variablesStr = "x=10; y=20; z=30";

        Variable varX = new Variable("x");
        Variable varY = new Variable("y");
        Variable varZ = new Variable("z");

        assertEquals(10, varX.evaluate(variablesStr));
        assertEquals(20, varY.evaluate(variablesStr));
        assertEquals(30, varZ.evaluate(variablesStr));
        assertEquals(10, varX.evaluate(variablesStr));
    }

    @Test
    void testEvaluateCaseSensitivity() {
        Variable varLower = new Variable("x");
        Variable varUpper = new Variable("X");

        assertEquals(10, varLower.evaluate("x=10; X=20"));
        assertEquals(20, varUpper.evaluate("x=10; X=20"));
    }

    @Test
    void testEvaluatePerformanceWithRepeatedCalls() {
        String complexVariables = "a=1; b=2; c=3; d=4; e=5; f=6; g=7; h=8; i=9; j=10";

        Variable[] variables = {
                new Variable("a"), new Variable("b"), new Variable("c"),
                new Variable("d"), new Variable("e"), new Variable("f"),
                new Variable("g"), new Variable("h"), new Variable("i"), new Variable("j")
        };
        for (int i = 0; i < 10; i++) {
            for (Variable var : variables) {
                assertDoesNotThrow(() -> var.evaluate(complexVariables));
            }
        }
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
    void testDerivativeWithDifferentVariables() {
        Variable varX = new Variable("x");
        assertEquals(1, varX.derivative("x").evaluate("x=5"));

        assertEquals(0, varX.derivative("y").evaluate("x=5; y=10"));

        assertEquals(0, varX.derivative("z").evaluate("x=5"));
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