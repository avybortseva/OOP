package ru.nsu.g.a.vybortseva.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskCreationWithValidDates() {
        Task task = new Task("2_1_1", "Простые числа", "2026-05-01", "2026-05-10");

        assertEquals("2_1_1", task.getId());
        assertEquals("Простые числа", task.getTitle());
        assertEquals(LocalDate.of(2026, 5, 1), task.getSoftDeadline());
        assertEquals(LocalDate.of(2026, 5, 10), task.getHardDeadline());
    }

    @Test
    void testTaskCreationWithDifferentDateFormat() {
        Task task = new Task("1_1_1", "Задача", "2025-12-31", "2026-01-15");

        assertEquals(LocalDate.of(2025, 12, 31), task.getSoftDeadline());
        assertEquals(LocalDate.of(2026, 1, 15), task.getHardDeadline());
    }

    @Test
    void testTaskWithSameDeadlines() {
        Task task = new Task("3_1_1", "Тест", "2026-06-01", "2026-06-01");

        assertEquals(LocalDate.of(2026, 6, 1), task.getSoftDeadline());
        assertEquals(LocalDate.of(2026, 6, 1), task.getHardDeadline());
    }

    @Test
    void testGetIdReturnsCorrectValue() {
        Task task = new Task("test_id_123", "Title", "2026-01-01", "2026-01-10");
        assertEquals("test_id_123", task.getId());
    }

    @Test
    void testGetTitleReturnsCorrectValue() {
        Task task = new Task("id", "Test Task Title", "2026-01-01", "2026-01-10");
        assertEquals("Test Task Title", task.getTitle());
    }

    @Test
    void testGetSoftDeadlineReturnsCorrectDate() {
        Task task = new Task("id", "title", "2026-03-15", "2026-03-20");
        assertEquals(LocalDate.of(2026, 3, 15), task.getSoftDeadline());
    }

    @Test
    void testGetHardDeadlineReturnsCorrectDate() {
        Task task = new Task("id", "title", "2026-03-15", "2026-03-20");
        assertEquals(LocalDate.of(2026, 3, 20), task.getHardDeadline());
    }
}
