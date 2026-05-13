package ru.nsu.g.a.vybortseva.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import ru.nsu.g.a.vybortseva.model.Config;

class ConfigLoaderTest {

    @TempDir
    Path tempDir;

    @Test
    void testLoadValidConfig() throws IOException {
        String configContent = """
                tasks {
                    task(id: '1_1_1', title: 'Test Task', soft: '2025-05-01', hard: '2025-05-10')
                }
                
                groups {
                    group(24213) {
                        student(name: 'Test Student', gitName: 'test-git', repoUrl: 'https://github.com/test/repo')
                    }
                }
                
                check {
                    group(24213) {
                        task '1_1_1'
                    }
                }
                
                checkPoints {
                    point(name: 'Test Point', date: '2026-05-01')
                }
                
                bonuses {
                    bonus(student: 'test-git', task: '1_1_1', points: 2.0)
                }
                """;

        Path configFile = tempDir.resolve("test.groovy");
        Files.writeString(configFile, configContent);

        ConfigLoader loader = new ConfigLoader();
        Config config = loader.load(configFile.toString());

        assertNotNull(config);
        assertEquals(1, config.getTasks().size());
        assertEquals(1, config.getGroups().size());
        assertEquals(1, config.getAssignments().size());
        assertEquals(1, config.getPoints().size());
        assertEquals(1, config.getBonuses().size());

        assertEquals("1_1_1", config.getTasks().get(0).getId());
        assertEquals("Test Task", config.getTasks().get(0).getTitle());
    }

    @Test
    void testLoadConfigWithMultipleTasks() throws IOException {
        String configContent = """
                tasks {
                    task(id: '1_1_1', title: 'Task 1', soft: '2025-05-01', hard: '2025-05-10')
                    task(id: '2_1_1', title: 'Task 2', soft: '2026-05-01', hard: '2026-05-10')
                }
                
                groups {
                    group(24213) {
                        student(name: 'Student 1', gitName: 'student1', repoUrl: 'https://github.com/student1/repo')
                        student(name: 'Student 2', gitName: 'student2', repoUrl: 'https://github.com/student2/repo')
                    }
                }
                
                check {
                    group(24213) {
                        task '1_1_1'
                        task '2_1_1'
                    }
                }
                """;

        Path configFile = tempDir.resolve("test.groovy");
        Files.writeString(configFile, configContent);

        ConfigLoader loader = new ConfigLoader();
        Config config = loader.load(configFile.toString());

        assertEquals(2, config.getTasks().size());
        assertEquals(1, config.getGroups().size());
        assertEquals(2, config.getGroups().get(0).getGroup().size());
        assertEquals(2, config.getAssignmentsForGroup(24213).size());
    }

    @Test
    void testLoadConfigWithCheckpoints() throws IOException {
        String configContent = """
                checkPoints {
                    point(name: 'First Month', date: '2026-03-01')
                    point(name: 'Middle Semester', date: '2026-04-15')
                    point(name: 'Final Exam', date: '2026-05-25')
                }
                """;

        Path configFile = tempDir.resolve("test.groovy");
        Files.writeString(configFile, configContent);

        ConfigLoader loader = new ConfigLoader();
        Config config = loader.load(configFile.toString());

        assertEquals(3, config.getPoints().size());
        assertEquals("First Month", config.getPoints().get(0).getName());
        assertEquals("Middle Semester", config.getPoints().get(1).getName());
        assertEquals("Final Exam", config.getPoints().get(2).getName());
    }

    @Test
    void testLoadConfigWithBonuses() throws IOException {
        String configContent = """
                tasks {
                    task(id: '1_1_1', title: 'Task', soft: '2025-05-01', hard: '2025-05-10')
                }
                
                groups {
                    group(24213) {
                        student(name: 'Student', gitName: 'student-git', repoUrl: 'https://github.com/student/repo')
                    }
                }
                
                bonuses {
                    bonus(student: 'student-git', task: '1_1_1', points: 2.0)
                    bonus(student: 'student-git', task: '1_1_1', points: 1.5)
                }
                """;

        Path configFile = tempDir.resolve("test.groovy");
        Files.writeString(configFile, configContent);

        ConfigLoader loader = new ConfigLoader();
        Config config = loader.load(configFile.toString());

        assertEquals(2, config.getBonuses().size());
        assertEquals(2.0, config.getBonuses().get(0).getPoints());
        assertEquals(1.5, config.getBonuses().get(1).getPoints());
    }

    @Test
    void testLoadEmptyConfig() throws IOException {
        String configContent = """
                tasks {
                }
                
                groups {
                }
                
                check {
                }
                """;

        Path configFile = tempDir.resolve("test.groovy");
        Files.writeString(configFile, configContent);

        ConfigLoader loader = new ConfigLoader();
        Config config = loader.load(configFile.toString());

        assertNotNull(config);
        assertTrue(config.getTasks().isEmpty());
        assertTrue(config.getGroups().isEmpty());
        assertTrue(config.getAssignments().isEmpty());
    }

    @Test
    void testLoadConfigWithInvalidPath() {
        ConfigLoader loader = new ConfigLoader();

        assertThrows(IOException.class, () -> {
            loader.load("non-existent-file.groovy");
        });
    }

    @Test
    void testLoadConfigWithSyntaxError() throws IOException {
        String configContent = """
                tasks {
                    task(id: '1_1_1', title: 'Test'  // missing closing parenthesis
                }
                """;

        Path configFile = tempDir.resolve("test.groovy");
        Files.writeString(configFile, configContent);

        ConfigLoader loader = new ConfigLoader();

        assertThrows(Exception.class, () -> {
            loader.load(configFile.toString());
        });
    }

    @Test
    void testLoadConfigWithMultipleGroups() throws IOException {
        String configContent = """
                groups {
                    group(24213) {
                        student(name: 'Student A', gitName: 'studentA', repoUrl: 'https://github.com/A/repo')
                    }
                    group(24214) {
                        student(name: 'Student B', gitName: 'studentB', repoUrl: 'https://github.com/B/repo')
                        student(name: 'Student C', gitName: 'studentC', repoUrl: 'https://github.com/C/repo')
                    }
                }
                """;

        Path configFile = tempDir.resolve("test.groovy");
        Files.writeString(configFile, configContent);

        ConfigLoader loader = new ConfigLoader();
        Config config = loader.load(configFile.toString());

        assertEquals(2, config.getGroups().size());
        assertEquals(1, config.getGroups().get(0).getGroup().size());
        assertEquals(2, config.getGroups().get(1).getGroup().size());
    }
}
