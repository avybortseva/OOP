package ru.nsu.g.a.vybortseva.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    private Group group;
    private Student student1;
    private Student student2;
    private Student student3;

    @BeforeEach
    void setUp() {
        group = new Group(24213);
        student1 = new Student("Иванов Иван", "ivanov-git", "https://github.com/ivanov/OOP");
        student2 = new Student("Петров Петр", "petrov-git", "https://github.com/petrov/OOP");
        student3 = new Student("Сидоров Сидор", "sidorov-git", "https://github.com/sidorov/OOP");
    }

    @Test
    void testGroupCreation() {
        Group newGroup = new Group(12345);

        assertEquals(12345, newGroup.getNumber());
        assertTrue(newGroup.getGroup().isEmpty());
    }

    @Test
    void testAddStudent() {
        group.addStudent(student1);

        assertEquals(1, group.getGroup().size());
        assertTrue(group.getGroup().contains(student1));
    }

    @Test
    void testAddMultipleStudents() {
        group.addStudent(student1);
        group.addStudent(student2);
        group.addStudent(student3);

        assertEquals(3, group.getGroup().size());
        assertTrue(group.getGroup().contains(student1));
        assertTrue(group.getGroup().contains(student2));
        assertTrue(group.getGroup().contains(student3));
    }

    @Test
    void testRemoveStudent() {
        group.addStudent(student1);
        group.addStudent(student2);
        group.removeStudent(student1);

        assertEquals(1, group.getGroup().size());
        assertFalse(group.getGroup().contains(student1));
        assertTrue(group.getGroup().contains(student2));
    }

    @Test
    void testRemoveStudentNotInGroup() {
        group.addStudent(student1);
        group.removeStudent(student2);

        assertEquals(1, group.getGroup().size());
        assertTrue(group.getGroup().contains(student1));
    }

    @Test
    void testRemoveLastStudent() {
        group.addStudent(student1);
        group.removeStudent(student1);

        assertTrue(group.getGroup().isEmpty());
    }

    @Test
    void testGetNumber() {
        assertEquals(24213, group.getNumber());
    }

    @Test
    void testGetGroupReturnsMutableList() {
        group.addStudent(student1);
        List<Student> students = group.getGroup();
        students.add(student2);

        assertEquals(2, group.getGroup().size());
        assertTrue(group.getGroup().contains(student2));
    }

    @Test
    void testGroupWithZeroNumber() {
        Group zeroGroup = new Group(0);

        assertEquals(0, zeroGroup.getNumber());
        assertTrue(zeroGroup.getGroup().isEmpty());
    }

    @Test
    void testGroupWithNegativeNumber() {
        Group negativeGroup = new Group(-1);

        assertEquals(-1, negativeGroup.getNumber());
    }

    @Test
    void testAddDuplicateStudent() {
        group.addStudent(student1);
        group.addStudent(student1);

        assertEquals(2, group.getGroup().size());
    }

    @Test
    void testClearAllStudents() {
        group.addStudent(student1);
        group.addStudent(student2);
        group.getGroup().clear();

        assertTrue(group.getGroup().isEmpty());
    }
}
