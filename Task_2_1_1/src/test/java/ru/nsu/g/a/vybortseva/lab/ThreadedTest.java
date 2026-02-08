package ru.nsu.g.a.vybortseva.lab;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты для реализации с помощью Thread.
 */
public class ThreadedTest {
    @Test
    void testHasCompositeNumber_allPrimes1() {
        Operation checker = new Threaded(1);
        long[] allPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        assertFalse(checker.hasCompositeNumber(allPrimes));
    }

    @Test
    void testHasCompositeNumber_allPrimes5() {
        Operation checker = new Threaded(5);
        long[] allPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        assertFalse(checker.hasCompositeNumber(allPrimes));
    }

    @Test
    void testHasCompositeNumber_allPrimes10() {
        Operation checker = new Threaded(10);
        long[] allPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        assertFalse(checker.hasCompositeNumber(allPrimes));
    }

    @Test
    void testHasCompositeNumber_allPrimes15() {
        Operation checker = new Threaded(15);
        long[] allPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        assertFalse(checker.hasCompositeNumber(allPrimes));
    }

    @Test
    void testHasCompositeNumber_allComposites1() {
        Operation checker = new Threaded(1);
        long[] allComposites = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18};
        assertTrue(checker.hasCompositeNumber(allComposites));
    }

    @Test
    void testHasCompositeNumber_allComposites10() {
        Operation checker = new Threaded(10);
        long[] allComposites = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18};
        assertTrue(checker.hasCompositeNumber(allComposites));
    }

    @Test
    void testHasCompositeNumber_allComposites11() {
        Operation checker = new Threaded(11);
        long[] allComposites = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18};
        assertTrue(checker.hasCompositeNumber(allComposites));
    }

    @Test
    void testHasCompositeNumber_mixedArray1() {
        Operation checker = new Threaded(1);
        long[] numbers = {4, 2, 3, 5, 7};
        assertTrue(checker.hasCompositeNumber(numbers));
    }

    @Test
    void testHasCompositeNumber_mixedArray10() {
        Operation checker = new Threaded(10);
        long[] numbers = {4, 2, 3, 5, 7};
        assertTrue(checker.hasCompositeNumber(numbers));
    }

    @Test
    void testHasCompositeNumber_mixedArray13() {
        Operation checker = new Threaded(13);
        long[] numbers = {4, 2, 3, 5, 7};
        assertTrue(checker.hasCompositeNumber(numbers));
    }

    @Test
    void testHasCompositeNumber_emptyArray5() {
        Operation checker = new Threaded(5);
        long[] empty = {};
        assertFalse(checker.hasCompositeNumber(empty));
    }

    @Test
    void testHasCompositeNumber_singlePrime() {
        Operation checker = new Threaded(3);
        long[] singlePrime = {13};
        assertFalse(checker.hasCompositeNumber(singlePrime));
    }

    @Test
    void testHasCompositeNumber_singleComposite() {
        Operation checker = new Threaded(1);
        long[] singleComposite = {15};
        assertTrue(checker.hasCompositeNumber(singleComposite));
    }

    @Test
    void testHasCompositeNumber_LargePrimeArray2() {
        Operation checker = new Threaded(2);
        long[] knownPrimes = {7919, 104729, 1299709, 15485863, 179424673};
        assertFalse(checker.hasCompositeNumber(knownPrimes));
    }

    @Test
    void testHasCompositeNumber_LargePrimeArray30() {
        Operation checker = new Threaded(30);
        long[] knownPrimes = {7919, 104729, 1299709, 15485863, 179424673};
        assertFalse(checker.hasCompositeNumber(knownPrimes));
    }

    @Test
    void testHasCompositeNumber_LargeMixedArray() {
        Operation checker = new Threaded(8);
        long[] numbers = {7919, 104729, 1299709, 15485863, 179424673, 1000000};
        assertTrue(checker.hasCompositeNumber(numbers));
    }
}