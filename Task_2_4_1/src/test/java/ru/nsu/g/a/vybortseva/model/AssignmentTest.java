package ru.nsu.g.a.vybortseva.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {

    private Assignment assignment;

    @BeforeEach
    void setUp() {
        assignment = new Assignment(24213);
    }

    @Test
    void testAssignmentCreation() {
        assertEquals(24213, assignment.getGroupNumber());
        assertTrue(assignment.getTaskIds().isEmpty());
    }

    @Test
    void testAddSingleTask() {
        assignment.addTask("1_1_1");

        assertEquals(1, assignment.getTaskIds().size());
        assertTrue(assignment.getTaskIds().contains("1_1_1"));
    }

    @Test
    void testAddMultipleTasks() {
        assignment.addTask("1_1_1");
        assignment.addTask("2_1_1");
        assignment.addTask("3_1_1");

        assertEquals(3, assignment.getTaskIds().size());
        assertTrue(assignment.getTaskIds().contains("1_1_1"));
        assertTrue(assignment.getTaskIds().contains("2_1_1"));
        assertTrue(assignment.getTaskIds().contains("3_1_1"));
    }

    @Test
    void testAddDuplicateTask() {
        assignment.addTask("1_1_1");
        assignment.addTask("1_1_1");

        assertEquals(2, assignment.getTaskIds().size());
    }

    @Test
    void testGetGroupNumber() {
        Assignment group24213 = new Assignment(24213);
        Assignment group24214 = new Assignment(24214);
        Assignment groupZero = new Assignment(0);
        Assignment groupNegative = new Assignment(-1);

        assertEquals(24213, group24213.getGroupNumber());
        assertEquals(24214, group24214.getGroupNumber());
        assertEquals(0, groupZero.getGroupNumber());
        assertEquals(-1, groupNegative.getGroupNumber());
    }

    @Test
    void testGetTaskIdsReturnsMutableList() {
        assignment.addTask("1_1_1");
        List<String> tasks = assignment.getTaskIds();
        tasks.add("2_1_1");

        assertEquals(2, assignment.getTaskIds().size());
        assertTrue(assignment.getTaskIds().contains("2_1_1"));
    }

    @Test
    void testAddTaskWithComplexId() {
        assignment.addTask("Task_2_1_1");
        assignment.addTask("lab_3");
        assignment.addTask("task-4");

        assertTrue(assignment.getTaskIds().contains("Task_2_1_1"));
        assertTrue(assignment.getTaskIds().contains("lab_3"));
        assertTrue(assignment.getTaskIds().contains("task-4"));
    }

    @Test
    void testClearAllTasks() {
        assignment.addTask("1_1_1");
        assignment.addTask("2_1_1");
        assignment.getTaskIds().clear();

        assertTrue(assignment.getTaskIds().isEmpty());
    }

    @Test
    void testRemoveTask() {
        assignment.addTask("1_1_1");
        assignment.addTask("2_1_1");
        assignment.getTaskIds().remove("1_1_1");

        assertEquals(1, assignment.getTaskIds().size());
        assertFalse(assignment.getTaskIds().contains("1_1_1"));
        assertTrue(assignment.getTaskIds().contains("2_1_1"));
    }

    @Test
    void testAssignmentWithEmptyTaskList() {
        assertTrue(assignment.getTaskIds().isEmpty());
    }

    @Test
    void testAddTaskWithEmptyString() {
        assignment.addTask("");

        assertEquals(1, assignment.getTaskIds().size());
        assertTrue(assignment.getTaskIds().contains(""));
    }

    @Test
    void testAddTaskWithNull() {
        assignment.addTask(null);

        assertEquals(1, assignment.getTaskIds().size());
        assertTrue(assignment.getTaskIds().contains(null));
    }

    @Test
    void testTaskIdsOrder() {
        assignment.addTask("first");
        assignment.addTask("second");
        assignment.addTask("third");

        List<String> tasks = assignment.getTaskIds();
        assertEquals("first", tasks.get(0));
        assertEquals("second", tasks.get(1));
        assertEquals("third", tasks.get(2));
    }
}
