package ru.nsu.g.a.vybortseva.lab;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты для функции проверки числа на простоту.
 */
public class OperationTest {
    @Test
    void testIsPrime_withNegativeNumber() {
        assertFalse(Operation.isPrime(-1));
        assertFalse(Operation.isPrime(-10));
        assertFalse(Operation.isPrime(-10000));
    }

    @Test
    void testIsPrime_withPrimes() {
        assertTrue(Operation.isPrime(2));
        assertTrue(Operation.isPrime(3));
        assertTrue(Operation.isPrime(5));
        assertTrue(Operation.isPrime(7));
        assertTrue(Operation.isPrime(11));
        assertTrue(Operation.isPrime(13));
        assertTrue(Operation.isPrime(17));
        assertTrue(Operation.isPrime(19));
        assertTrue(Operation.isPrime(23));
    }

    @Test
    void testIsPrime_withComposites() {
        assertFalse(Operation.isPrime(0));
        assertFalse(Operation.isPrime(1));

        assertFalse(Operation.isPrime(4));
        assertFalse(Operation.isPrime(6));
        assertFalse(Operation.isPrime(8));
        assertFalse(Operation.isPrime(9));
        assertFalse(Operation.isPrime(10));
        assertFalse(Operation.isPrime(12));
        assertFalse(Operation.isPrime(14));
        assertFalse(Operation.isPrime(15));
        assertFalse(Operation.isPrime(16));
        assertFalse(Operation.isPrime(18));
        assertFalse(Operation.isPrime(20));
    }

    @Test
    void testIsPrime_withLargePrimes() {
        assertTrue(Operation.isPrime(7919));
        assertTrue(Operation.isPrime(104729));
        assertTrue(Operation.isPrime(1299709));
    }

    @Test
    void testIsPrime_withLargeComposites() {
        assertFalse(Operation.isPrime(1000000));
        assertFalse(Operation.isPrime(999999));
        assertFalse(Operation.isPrime(104730));
        // 999983 * 999979 = 999962000117
        assertFalse(Operation.isPrime(999962000117L));
    }
}
