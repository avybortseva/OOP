package ru.nsu.g.a.vybortseva.lab;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Интерфейс для реализаций работы с простыми числами.
 */
public class OperationTest {
    @Test
    void testIsPrime_withNegativeNumber() {
        Operation checker = new Sequential();
        assertFalse(checker.isPrime(-1));
        assertFalse(checker.isPrime(-10));
        assertFalse(checker.isPrime(-10000));
    }

    @Test
    void testIsPrime_withPrimes() {
        Operation checker = new Sequential();

        assertTrue(checker.isPrime(2));
        assertTrue(checker.isPrime(3));
        assertTrue(checker.isPrime(5));
        assertTrue(checker.isPrime(7));
        assertTrue(checker.isPrime(11));
        assertTrue(checker.isPrime(13));
        assertTrue(checker.isPrime(17));
        assertTrue(checker.isPrime(19));
        assertTrue(checker.isPrime(23));
    }

    @Test
    void testIsPrime_withComposites() {
        Operation checker = new Sequential();

        assertFalse(checker.isPrime(0));
        assertFalse(checker.isPrime(1));

        assertFalse(checker.isPrime(4));
        assertFalse(checker.isPrime(6));
        assertFalse(checker.isPrime(8));
        assertFalse(checker.isPrime(9));
        assertFalse(checker.isPrime(10));
        assertFalse(checker.isPrime(12));
        assertFalse(checker.isPrime(14));
        assertFalse(checker.isPrime(15));
        assertFalse(checker.isPrime(16));
        assertFalse(checker.isPrime(18));
        assertFalse(checker.isPrime(20));
    }

    @Test
    void testIsPrime_withLargePrimes() {
        Operation checker = new Sequential();
        assertTrue(checker.isPrime(7919));
        assertTrue(checker.isPrime(104729));
        assertTrue(checker.isPrime(1299709));
    }

    @Test
    void testIsPrime_withLargeComposites() {
        Operation checker = new Sequential();
        assertFalse(checker.isPrime(1000000));
        assertFalse(checker.isPrime(999999));
        assertFalse(checker.isPrime(104730));
    }
}