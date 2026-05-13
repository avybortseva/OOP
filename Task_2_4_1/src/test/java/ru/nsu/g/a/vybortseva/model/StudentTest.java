package ru.nsu.g.a.vybortseva.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testStudentCreation() {
        Student student = new Student("Иванов Иван", "ivanov-git", "https://github.com/ivanov/OOP");

        assertEquals("Иванов Иван", student.getName());
        assertEquals("ivanov-git", student.getGitName());
        assertEquals("https://github.com/ivanov/OOP", student.getRepoUrl());
    }

    @Test
    void testStudentWithDifferentNames() {
        Student student = new Student("Петров Петр", "petrov-git", "https://github.com/petrov/OOP");

        assertEquals("Петров Петр", student.getName());
        assertEquals("petrov-git", student.getGitName());
    }

    @Test
    void testStudentWithLongRepositoryUrl() {
        String longUrl = "https://github.com/very-long-username/very-long-repository-name-OOP";
        Student student = new Student("Тестов Тест", "test-git", longUrl);

        assertEquals(longUrl, student.getRepoUrl());
    }

    @Test
    void testStudentWithGitHubHttpsUrl() {
        Student student = new Student("Маркидонов В.", "markidonov-git", "https://github.com/PytoByte/OOP");

        assertTrue(student.getRepoUrl().startsWith("https://github.com/"));
        assertTrue(student.getRepoUrl().endsWith("/OOP"));
    }

    @Test
    void testGetNameReturnsCorrectValue() {
        Student student = new Student("Выборцева Анастасия", "avybortseva", "https://github.com/avybortseva/OOP");
        assertEquals("Выборцева Анастасия", student.getName());
    }

    @Test
    void testGetGitNameReturnsCorrectValue() {
        Student student = new Student("Иванов Иван", "ivan-ivanov", "https://github.com/ivan-ivanov/OOP");
        assertEquals("ivan-ivanov", student.getGitName());
    }

    @Test
    void testGetRepoUrlReturnsCorrectValue() {
        Student student = new Student("Стубарев Илья", "stubarev-git", "https://github.com/IlyaStub/OOP");
        assertEquals("https://github.com/IlyaStub/OOP", student.getRepoUrl());
    }

    @Test
    void testStudentWithEmptyStrings() {
        Student student = new Student("", "", "");

        assertEquals("", student.getName());
        assertEquals("", student.getGitName());
        assertEquals("", student.getRepoUrl());
    }

    @Test
    void testStudentWithSpecialCharactersInName() {
        Student student = new Student("Ёжиков Ёж Ёжикович", "ezhik-git", "https://github.com/ezhik/OOP");

        assertEquals("Ёжиков Ёж Ёжикович", student.getName());
        assertEquals("ezhik-git", student.getGitName());
    }
}
