package ru.nsu.g.a.vybortseva.equations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;
import ru.nsu.g.a.vybortseva.equations.exceptions.UndefinedVariableException;

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
            new Variable("g"), new Variable("h"), new Variable("i"),
            new Variable("j")
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

    @Test
    void testEvaluateWithEmptyVariables() {
        Variable var = new Variable("x");

        assertThrows(UndefinedVariableException.class, () -> {
            var.evaluate("");
        });
        assertThrows(UndefinedVariableException.class, () -> {
            var.evaluate(null);
        });
    }

    void testEvaluateWithInvalidAssignments() {
        Variable var = new Variable("x");

        assertThrows(UndefinedVariableException.class, () -> {
            var.evaluate("y=10");
        });

        assertThrows(UndefinedVariableException.class, () -> {
            var.evaluate("");
        });

        assertThrows(UndefinedVariableException.class, () -> {
            var.evaluate(null);
        });

        assertThrows(UndefinedVariableException.class, () -> {
            var.evaluate("   ");
        });
    }

    @Test
    void testEvaluateBoundaryValues() {
        Variable var = new Variable("x");

        assertEquals(0, var.evaluate("x=0"));
        assertEquals(-1, var.evaluate("x=-1"));
        assertEquals(Integer.MAX_VALUE, var.evaluate("x=" + Integer.MAX_VALUE));
        assertEquals(Integer.MIN_VALUE, var.evaluate("x=" + Integer.MIN_VALUE));
    }

    @Test
    void testEvaluateDuplicateVariables() {
        Variable var = new Variable("x");

        assertEquals(30, var.evaluate("x=10; x=20; x=30"));
        assertEquals(70, var.evaluate("x=50; y=60; x=70")); // Последнее значение x=70
    }

    @Test
    void testDerivativeEdgeCases() {
        Variable var = new Variable("x");

        assertEquals(1, var.derivative("x").evaluate(""));
        assertEquals(0, var.derivative("y").evaluate(""));
        assertEquals(0, var.derivative("X").evaluate(""));
    }

    @Test
    void testEvaluateWithLargeNumbers() {
        Variable var = new Variable("x");

        assertEquals(1000000, var.evaluate("x=1000000"));
        assertEquals(-1000000, var.evaluate("x=-1000000"));
    }

    @Test
    void testEvaluateCacheWithDifferentStrings() {
        Variable var = new Variable("x");

        assertEquals(10, var.evaluate("x=10"));
        assertEquals(10, var.evaluate("x=10"));
        assertEquals(20, var.evaluate("x=20"));
        assertEquals(20, var.evaluate("x=20"));
    }

    @Test
    void testParseVariablesWithValidInput() {
        Variable var = new Variable("x");

        assertDoesNotThrow(() -> var.parseVariables("x=10"));
        assertDoesNotThrow(() -> var.parseVariables("x=10; y=20"));
        assertDoesNotThrow(() -> var.parseVariables("x = 10 ; y = 20"));
    }

    @Test
    void testParseVariablesWithEmptyAndNull() {
        Variable var = new Variable("x");

        assertDoesNotThrow(() -> {
            Map<String, Integer> result = var.parseVariables("");
            assertTrue(result.isEmpty());
        });

        assertDoesNotThrow(() -> {
            Map<String, Integer> result = var.parseVariables(null);
            assertTrue(result.isEmpty());
        });

        assertDoesNotThrow(() -> {
            Map<String, Integer> result = var.parseVariables("   ");
            assertTrue(result.isEmpty());
        });
    }

    @Test
    void testParseVariablesResultContent() {
        Variable var = new Variable("x");

        Map<String, Integer> result1 = var.parseVariables("x=10");
        assertEquals(1, result1.size());
        assertEquals(10, result1.get("x"));

        Map<String, Integer> result2 = var.parseVariables("x=10; y=20; z=30");
        assertEquals(3, result2.size());
        assertEquals(10, result2.get("x"));
        assertEquals(20, result2.get("y"));
        assertEquals(30, result2.get("z"));

        Map<String, Integer> result3 = var.parseVariables("a=1; b=2; a=3");
        assertEquals(2, result3.size());
        assertEquals(3, result3.get("a"));
        assertEquals(2, result3.get("b"));
    }

    @Test
    void testParseVariablesBoundaryValues() {
        Variable var = new Variable("x");

        Map<String, Integer> result1 = var.parseVariables("x=0");
        assertEquals(0, result1.get("x"));

        Map<String, Integer> result2 = var.parseVariables("x=-1");
        assertEquals(-1, result2.get("x"));

        Map<String, Integer> result3 = var.parseVariables("x=" + Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, result3.get("x"));

        Map<String, Integer> result4 = var.parseVariables("x=" + Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, result4.get("x"));
    }
}