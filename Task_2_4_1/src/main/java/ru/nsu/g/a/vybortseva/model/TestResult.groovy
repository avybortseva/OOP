package ru.nsu.g.a.vybortseva.model

class TestResult {
    private final String taskId;
    private final boolean buildSuccess;
    private final int testPassed;
    private final int testsTotal;

    TestResult(String taskId, boolean buildSuccess, int testPassed, int testsTotal) {
        this.taskId = taskId
        this.buildSuccess = buildSuccess
        this.testPassed = testPassed
        this.testsTotal = testsTotal
    }

    String getTaskId() {
        return taskId
    }

    boolean getBuildSuccess() {
        return buildSuccess
    }

    int getTestPassed() {
        return testPassed
    }

    int getTestsTotal() {
        return testsTotal
    }
}
