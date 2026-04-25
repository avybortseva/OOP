package ru.nsu.g.a.vybortseva.model

/**
 * Data holder for the execution results of automated tests.
 */
class TestResult {
    private final String taskId;
    private final boolean buildSuccess;
    private final int testPassed;
    private final int testsTotal;

    /**
     * Constructs a TestResult with execution statistics.
     */
    TestResult(String taskId, boolean buildSuccess, int testPassed, int testsTotal) {
        this.taskId = taskId
        this.buildSuccess = buildSuccess
        this.testPassed = testPassed
        this.testsTotal = testsTotal
    }

    /**
     * Return the ID of the task.
     */
    String getTaskId() {
        return taskId
    }

    /**
     * Return true if the build succeeded.
     */
    boolean getBuildSuccess() {
        return buildSuccess
    }

    /**
     * Return number of tests passed.
     */
    int getTestPassed() {
        return testPassed
    }

    /**
     * Return total number of tests.
     */
    int getTestsTotal() {
        return testsTotal
    }
}
