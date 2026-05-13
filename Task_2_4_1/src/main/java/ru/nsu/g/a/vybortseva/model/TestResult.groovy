package ru.nsu.g.a.vybortseva.model;

/**
 * Represents the result of running tests and build checks for a specific task.
 */
public class TestResult {
    private final String taskId;
    private final boolean buildSuccess;
    private final int testPassed;
    private final int testsTotal;
    private final String status; // "OK", "COMPILE_ERROR", "JAVADOC_ERROR", "TEST_ERROR"

    /**
     * Constructs a TestResult with comprehensive build and test outcomes.
     */
    public TestResult(String taskId, boolean buildSuccess,
                      int testPassed, int testsTotal, String status) {
        this.taskId = taskId;
        this.buildSuccess = buildSuccess;
        this.testPassed = testPassed;
        this.testsTotal = testsTotal;
        this.status = status;
    }

    /**
     * Returns the overall status of the test run.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns whether the build (compilation) was successful.
     */
    public boolean isBuildSuccess() {
        return buildSuccess;
    }

    /**
     * Returns the number of tests that passed successfully.
     */
    public int getTestPassed() {
        return testPassed;
    }

    /**
     * Returns the total number of tests that were executed.
     */
    public int getTestsTotal() {
        return testsTotal;
    }

    /**
     * Returns the unique identifier of the task this result belongs to.
     */
    public String getTaskId() {
        return taskId;
    }
}
