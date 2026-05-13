package ru.nsu.g.a.vybortseva.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class ConfigTest {

    private Config config;
    private Task task1;
    private Task task2;
    private Group group1;
    private Group group2;
    private Student student1;
    private Student student2;
    private CheckPoint point1;
    private CheckPoint point2;
    private Assignment assignment1;
    private Assignment assignment2;
    private Bonus bonus1;
    private Bonus bonus2;

    @BeforeEach
    void setUp() {
        config = new Config();

        task1 = new Task("1_1_1", "Задача 1", "2025-05-01", "2025-05-10");
        task2 = new Task("2_1_1", "Задача 2", "2026-05-01", "2026-05-10");

        group1 = new Group(24213);
        group2 = new Group(24214);
        student1 = new Student("Иванов Иван", "ivanov-git", "https://github.com/ivanov/OOP");
        student2 = new Student("Петров Петр", "petrov-git", "https://github.com/petrov/OOP");
        group1.addStudent(student1);
        group2.addStudent(student2);

        point1 = new CheckPoint("Первый месяц", "2026-03-01");
        point2 = new CheckPoint("Середина семестра", "2026-04-15");

        assignment1 = new Assignment(24213);
        assignment1.addTask("1_1_1");
        assignment1.addTask("2_1_1");

        assignment2 = new Assignment(24214);
        assignment2.addTask("2_1_1");

        bonus1 = new Bonus("ivanov-git", "1_1_1", 2.0);
        bonus2 = new Bonus("petrov-git", "2_1_1", 1.5);
    }

    @Test
    void testConfigCreation() {
        assertNotNull(config.getTasks());
        assertNotNull(config.getGroups());
        assertNotNull(config.getPoints());
        assertNotNull(config.getAssignments());
        assertNotNull(config.getBonuses());
        assertTrue(config.getTasks().isEmpty());
        assertTrue(config.getGroups().isEmpty());
    }

    @Test
    void testAddTasks() {
        config.getTasks().add(task1);
        config.getTasks().add(task2);

        assertEquals(2, config.getTasks().size());
        assertTrue(config.getTasks().contains(task1));
        assertTrue(config.getTasks().contains(task2));
    }

    @Test
    void testAddGroups() {
        config.getGroups().add(group1);
        config.getGroups().add(group2);

        assertEquals(2, config.getGroups().size());
        assertTrue(config.getGroups().contains(group1));
        assertTrue(config.getGroups().contains(group2));
    }

    @Test
    void testAddCheckPoints() {
        config.getPoints().add(point1);
        config.getPoints().add(point2);

        assertEquals(2, config.getPoints().size());
        assertEquals("Первый месяц", config.getPoints().get(0).getName());
        assertEquals("Середина семестра", config.getPoints().get(1).getName());
    }

    @Test
    void testAddAssignments() {
        config.getAssignments().add(assignment1);
        config.getAssignments().add(assignment2);

        assertEquals(2, config.getAssignments().size());
    }

    @Test
    void testAddBonuses() {
        config.getBonuses().add(bonus1);
        config.getBonuses().add(bonus2);

        assertEquals(2, config.getBonuses().size());
        assertEquals("ivanov-git", config.getBonuses().get(0).getStudentGitName());
        assertEquals("petrov-git", config.getBonuses().get(1).getStudentGitName());
    }

    @Test
    void testGetAssignmentsForGroup() {
        config.getAssignments().add(assignment1);
        config.getAssignments().add(assignment2);

        List<String> tasksForGroup24213 = config.getAssignmentsForGroup(24213);
        List<String> tasksForGroup24214 = config.getAssignmentsForGroup(24214);
        List<String> tasksForNonExistentGroup = config.getAssignmentsForGroup(99999);

        assertEquals(2, tasksForGroup24213.size());
        assertTrue(tasksForGroup24213.contains("1_1_1"));
        assertTrue(tasksForGroup24213.contains("2_1_1"));

        assertEquals(1, tasksForGroup24214.size());
        assertTrue(tasksForGroup24214.contains("2_1_1"));

        assertTrue(tasksForNonExistentGroup.isEmpty());
    }

    @Test
    void testGetAssignmentsForGroupWithMultipleAssignmentsSameGroup() {
        Assignment assignment1a = new Assignment(24213);
        assignment1a.addTask("3_1_1");

        config.getAssignments().add(assignment1);
        config.getAssignments().add(assignment1a);

        List<String> tasks = config.getAssignmentsForGroup(24213);

        assertEquals(3, tasks.size());
        assertTrue(tasks.contains("1_1_1"));
        assertTrue(tasks.contains("2_1_1"));
        assertTrue(tasks.contains("3_1_1"));
    }

    @Test
    void testGetAssignmentsForGroupWithNoTasks() {
        Assignment emptyAssignment = new Assignment(24213);
        config.getAssignments().add(emptyAssignment);

        List<String> tasks = config.getAssignmentsForGroup(24213);

        assertTrue(tasks.isEmpty());
    }

    @Test
    void testGettersReturnSameObjects() {
        config.getTasks().add(task1);
        config.getGroups().add(group1);
        config.getPoints().add(point1);
        config.getAssignments().add(assignment1);
        config.getBonuses().add(bonus1);

        assertSame(task1, config.getTasks().get(0));
        assertSame(group1, config.getGroups().get(0));
        assertSame(point1, config.getPoints().get(0));
        assertSame(assignment1, config.getAssignments().get(0));
        assertSame(bonus1, config.getBonuses().get(0));
    }

    @Test
    void testListsAreMutable() {
        config.getTasks().add(task1);
        config.getTasks().add(task2);
        config.getTasks().remove(task1);

        assertEquals(1, config.getTasks().size());
        assertTrue(config.getTasks().contains(task2));
    }

    @Test
    void testGetAssignmentsForGroupWithNoAssignments() {
        List<String> tasks = config.getAssignmentsForGroup(24213);
        assertTrue(tasks.isEmpty());
    }
}