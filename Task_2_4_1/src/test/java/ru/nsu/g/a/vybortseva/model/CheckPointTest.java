package ru.nsu.g.a.vybortseva.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CheckPointTest {

    @Test
    void testCheckPointCreation() {
        CheckPoint point = new CheckPoint("Первый месяц", "2026-03-01");

        assertEquals("Первый месяц", point.getName());
        assertEquals(LocalDate.of(2026, 3, 1), point.getDate());
    }

    @Test
    void testCheckPointWithDifferentName() {
        CheckPoint point = new CheckPoint("Середина семестра", "2026-04-15");

        assertEquals("Середина семестра", point.getName());
        assertEquals(LocalDate.of(2026, 4, 15), point.getDate());
    }

    @Test
    void testCheckPointWithFinalDate() {
        CheckPoint point = new CheckPoint("Финальный зачет", "2026-05-25");

        assertEquals("Финальный зачет", point.getName());
        assertEquals(LocalDate.of(2026, 5, 25), point.getDate());
    }

    @Test
    void testCheckPointWithLeapYearDate() {
        CheckPoint point = new CheckPoint("Високосный год", "2024-02-29");

        assertEquals(LocalDate.of(2024, 2, 29), point.getDate());
    }

    @Test
    void testCheckPointWithPastDate() {
        CheckPoint point = new CheckPoint("Прошедшая дата", "2020-01-01");

        assertEquals(LocalDate.of(2020, 1, 1), point.getDate());
    }

    @Test
    void testCheckPointWithFutureDate() {
        CheckPoint point = new CheckPoint("Будущая дата", "2030-12-31");

        assertEquals(LocalDate.of(2030, 12, 31), point.getDate());
    }

    @Test
    void testGetNameReturnsCorrectValue() {
        CheckPoint point = new CheckPoint("Тестовая точка", "2026-06-01");
        assertEquals("Тестовая точка", point.getName());
    }

    @Test
    void testGetDateReturnsCorrectValue() {
        CheckPoint point = new CheckPoint("Дата", "2025-10-15");
        assertEquals(LocalDate.of(2025, 10, 15), point.getDate());
    }

    @Test
    void testCheckPointWithShortName() {
        CheckPoint point = new CheckPoint("КТ", "2026-07-01");

        assertEquals("КТ", point.getName());
        assertEquals(LocalDate.of(2026, 7, 1), point.getDate());
    }

    @Test
    void testCheckPointWithLongName() {
        String longName = "Очень длинное название контрольной точки для проверки";
        CheckPoint point = new CheckPoint(longName, "2026-08-01");

        assertEquals(longName, point.getName());
        assertEquals(LocalDate.of(2026, 8, 1), point.getDate());
    }
}
