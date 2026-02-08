package ru.nsu.g.a.vybortseva.lab;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты для последовательной реализации.
 */
public class SequentialTest {
    @Test
    void testHasCompositeNumber_allPrimes() {
        Operation checker = new Sequential();
        long[] allPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        assertFalse(checker.hasCompositeNumber(allPrimes));
    }

    @Test
    void testHasCompositeNumber_allComposites() {
        Operation checker = new Sequential();
        long[] allComposites = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18};
        assertTrue(checker.hasCompositeNumber(allComposites));
    }

    @Test
    void testHasCompositeNumber_mixedArray1() {
        Operation checker = new Sequential();
        long[] numbers = {4, 2, 3, 5, 7};
        assertTrue(checker.hasCompositeNumber(numbers));
    }

    @Test
    void testHasCompositeNumber_mixedArray2() {
        Operation checker = new Sequential();
        long[] numbers = {2, 3, 4, 5, 7};
        assertTrue(checker.hasCompositeNumber(numbers));
    }

    @Test
    void testHasCompositeNumber_emptyArray() {
        Operation checker = new Sequential();
        long[] empty = {};
        assertFalse(checker.hasCompositeNumber(empty));
    }

    @Test
    void testHasCompositeNumber_singlePrime() {
        Operation checker = new Sequential();
        long[] singlePrime = {13};
        assertFalse(checker.hasCompositeNumber(singlePrime));
    }

    @Test
    void testHasCompositeNumber_singleComposite() {
        Operation checker = new Sequential();
        long[] singleComposite = {15};
        assertTrue(checker.hasCompositeNumber(singleComposite));
    }

    @Test
    void testHasCompositeNumber_LargePrimeArray() {
        Operation checker = new Sequential();
        long[] knownPrimes = {7919, 104729, 1299709, 15485863, 179424673};
        assertFalse(checker.hasCompositeNumber(knownPrimes));
    }

    @Test
    void testHasCompositeNumber_LargeMixedArray() {
        Operation checker = new Sequential();
        long[] numbers = {7919, 104729, 1299709, 15485863, 179424673, 1000000};
        assertTrue(checker.hasCompositeNumber(numbers));
    }
}
