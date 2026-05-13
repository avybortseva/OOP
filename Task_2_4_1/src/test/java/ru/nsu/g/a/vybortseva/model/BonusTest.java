package ru.nsu.g.a.vybortseva.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class BonusTest {

    @Test
    void testBonusCreationWithIntegerPoints() {
        Bonus bonus = new Bonus("ivanov-git", "2_1_1", 2.0);

        assertEquals("ivanov-git", bonus.getStudentGitName());
        assertEquals("2_1_1", bonus.getTaskId());
        assertEquals(2.0, bonus.getPoints());
    }

    @Test
    void testBonusCreationWithDoublePoints() {
        Bonus bonus = new Bonus("petrov-git", "1_1_1", 1.5);

        assertEquals("petrov-git", bonus.getStudentGitName());
        assertEquals("1_1_1", bonus.getTaskId());
        assertEquals(1.5, bonus.getPoints());
    }

    @Test
    void testBonusWithZeroPoints() {
        Bonus bonus = new Bonus("sidorov-git", "3_1_1", 0.0);

        assertEquals(0.0, bonus.getPoints());
    }

    @Test
    void testBonusWithNegativePoints() {
        Bonus bonus = new Bonus("student-git", "task_id", -1.0);

        assertEquals(-1.0, bonus.getPoints());
    }

    @Test
    void testGetStudentGitNameReturnsCorrectValue() {
        Bonus bonus = new Bonus("vybortseva-git", "2_1_1", 3.0);
        assertEquals("vybortseva-git", bonus.getStudentGitName());
    }

    @Test
    void testGetTaskIdReturnsCorrectValue() {
        Bonus bonus = new Bonus("student", "Task_2_1_1", 1.0);
        assertEquals("Task_2_1_1", bonus.getTaskId());
    }

    @Test
    void testGetPointsReturnsCorrectValue() {
        Bonus bonus = new Bonus("student", "task", 2.75);
        assertEquals(2.75, bonus.getPoints());
    }

    @Test
    void testBonusWithDifferentGitNames() {
        Bonus bonus1 = new Bonus("ivanov", "task1", 1.0);
        Bonus bonus2 = new Bonus("petrov", "task2", 2.0);

        assertEquals("ivanov", bonus1.getStudentGitName());
        assertEquals("petrov", bonus2.getStudentGitName());
        assertNotEquals(bonus1.getStudentGitName(), bonus2.getStudentGitName());
    }

    @Test
    void testBonusWithDifferentTaskIds() {
        Bonus bonus1 = new Bonus("student", "1_1_1", 1.0);
        Bonus bonus2 = new Bonus("student", "2_1_1", 1.0);

        assertEquals("1_1_1", bonus1.getTaskId());
        assertEquals("2_1_1", bonus2.getTaskId());
        assertNotEquals(bonus1.getTaskId(), bonus2.getTaskId());
    }

    @Test
    void testBonusWithLargePoints() {
        Bonus bonus = new Bonus("student", "task", 999.99);
        assertEquals(999.99, bonus.getPoints());
    }

    @Test
    void testBonusWithEmptyStrings() {
        Bonus bonus = new Bonus("", "", 0.0);

        assertEquals("", bonus.getStudentGitName());
        assertEquals("", bonus.getTaskId());
        assertEquals(0.0, bonus.getPoints());
    }

    @Test
    void testBonusWithNullValues() {
        Bonus bonus = new Bonus(null, null, 0.0);

        assertNull(bonus.getStudentGitName());
        assertNull(bonus.getTaskId());
        assertEquals(0.0, bonus.getPoints());
    }
}
