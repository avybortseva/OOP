package ru.nsu.g.a.vybortseva.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestResultTest {

    @Test
    void testTestResultCreationWithOkStatus() {
        TestResult result = new TestResult("2_1_1", true, 10, 10, "OK");

        assertEquals("2_1_1", result.getTaskId());
        assertTrue(result.isBuildSuccess());
        assertEquals(10, result.getTestPassed());
        assertEquals(10, result.getTestsTotal());
        assertEquals("OK", result.getStatus());
    }

    @Test
    void testTestResultWithCompileError() {
        TestResult result = new TestResult("1_1_1", false, 0, 0, "COMPILE_ERROR");

        assertFalse(result.isBuildSuccess());
        assertEquals(0, result.getTestPassed());
        assertEquals(0, result.getTestsTotal());
        assertEquals("COMPILE_ERROR", result.getStatus());
    }

    @Test
    void testTestResultWithJavadocError() {
        TestResult result = new TestResult("2_1_1", true, 0, 0, "JAVADOC_ERROR");

        assertTrue(result.isBuildSuccess());
        assertEquals(0, result.getTestPassed());
        assertEquals("JAVADOC_ERROR", result.getStatus());
    }

    @Test
    void testTestResultWithTestError() {
        TestResult result = new TestResult("3_1_1", true, 5, 10, "TEST_ERROR");

        assertTrue(result.isBuildSuccess());
        assertEquals(5, result.getTestPassed());
        assertEquals(10, result.getTestsTotal());
        assertEquals("TEST_ERROR", result.getStatus());
    }

    @Test
    void testTestResultWithPartialTestsPassed() {
        TestResult result = new TestResult("2_1_1", true, 7, 10, "OK");

        assertEquals(7, result.getTestPassed());
        assertEquals(10, result.getTestsTotal());
        assertEquals(3, result.getTestsTotal() - result.getTestPassed());
    }

    @Test
    void testTestResultWithZeroTests() {
        TestResult result = new TestResult("1_1_1", true, 0, 0, "OK");

        assertEquals(0, result.getTestPassed());
        assertEquals(0, result.getTestsTotal());
    }

    @Test
    void testGetTaskIdReturnsCorrectValue() {
        TestResult result = new TestResult("task_123", true, 5, 5, "OK");
        assertEquals("task_123", result.getTaskId());
    }

    @Test
    void testIsBuildSuccessReturnsCorrectValue() {
        TestResult successResult = new TestResult("id", true, 0, 0, "OK");
        TestResult failResult = new TestResult("id", false, 0, 0, "COMPILE_ERROR");

        assertTrue(successResult.isBuildSuccess());
        assertFalse(failResult.isBuildSuccess());
    }

    @Test
    void testGetTestPassedReturnsCorrectValue() {
        TestResult result = new TestResult("id", true, 8, 10, "OK");
        assertEquals(8, result.getTestPassed());
    }

    @Test
    void testGetTestsTotalReturnsCorrectValue() {
        TestResult result = new TestResult("id", true, 5, 15, "OK");
        assertEquals(15, result.getTestsTotal());
    }

    @Test
    void testGetStatusReturnsCorrectValue() {
        TestResult okResult = new TestResult("id", true, 10, 10, "OK");
        TestResult errorResult = new TestResult("id", false, 0, 0, "COMPILE_ERROR");

        assertEquals("OK", okResult.getStatus());
        assertEquals("COMPILE_ERROR", errorResult.getStatus());
    }
}
