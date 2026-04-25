package ru.nsu.g.a.vybortseva;
import java.io.File;
import ru.nsu.g.a.vybortseva.model.Config;
import ru.nsu.g.a.vybortseva.model.Group;
import ru.nsu.g.a.vybortseva.model.Student;
import ru.nsu.g.a.vybortseva.service.ConfigLoader;
import ru.nsu.g.a.vybortseva.service.GitService;
import ru.nsu.g.a.vybortseva.service.TestService;

/**
 * Entry point of the application that coordinates DSL loading,
 * repository synchronization, and automated testing.
 */
public class Main {
    /**
     * Main execution loop that processes students and tasks according to configuration.
     */
    public static void main(String[] args) {
        String configPath = "course.groovy";
        String rootReposDir = "repos";

        try {
            ConfigLoader loader = new ConfigLoader();
            Config config = loader.load(configPath);
            System.out.println("Загружено задач: " + config.getTasks().size());
            System.out.println("Загружено групп: " + config.getGroups().size());

            System.out.println("\nСинхронизация с Git...");
            GitService gitService = new GitService(rootReposDir);
            gitService.updateAll(config);

            System.out.println("\nЗапуск тестирования студентов...");
            TestService testService = new TestService();

            for (Group group : config.getGroups()) {
                System.out.println("\nПроверка группы: " + group.getNumber());

                for (Student student : group.getGroup()) {
                    File studentDir = new File(rootReposDir + "/"
                            + group.getNumber() + "/" + student.getGitName());
                    for (String taskId : config.getAssignmentsForGroup(group.getNumber())) {
                        String folderName = "Task_" + taskId.replace(".", "_");
                        File taskDir = new File(studentDir, folderName);

                        System.out.print("  Задача " + taskId + " -> ");

                        if (taskDir.exists()) {
                            boolean success = testService.runTests(taskDir);
                            if (success) {
                                System.out.println("УСПЕХ");
                            } else {
                                System.out.println("ПРОВАЛ");
                            }
                        } else {
                            System.out.println("ПАПКА НЕ НАЙДЕНА (" + folderName + ")");
                        }
                    }
                }
            }

            System.out.println("\nПроверка завершена.");

        } catch (Exception e) {
            System.err.println("Критическая ошибка при работе программы:");
            e.printStackTrace();
        }
    }
}