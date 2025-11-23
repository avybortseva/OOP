package ru.nsu.g.a.vybortseva.book;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТИРОВАНИЕ ЭЛЕКТРОННОЙ ЗАЧЕТНОЙ КНИЖКИ ===\n");

        System.out.println("ТЕСТ 1: Создание студента на платной форме");
        Student student = new Student("Иван", "Иванов", 12345, EducationForm.PAID);
        printStudentInfo(student);
        System.out.println();

        System.out.println("ТЕСТ 2: первый семестр");
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Императивное программирование", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Декларативное программирование", RecordType.DIFF_CREDIT, Grade.GOOD));
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математический анализ", RecordType.DIFF_CREDIT, Grade.SATISFACTORY));
        printAcademicHistory(student);
        printCalculations(student);
        System.out.println();

        System.out.println("ТЕСТ 3: Второй семестр");
        student.moveToNextSemester();
        student.addAcademicRecord(new AcademicRecord(Semester.SECOND_SEMESTER,
                "Дискретная математика", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.SECOND_SEMESTER,
                "Математический анализ", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.SECOND_SEMESTER,
                "История", RecordType.DIFF_CREDIT, Grade.GOOD));
        printAcademicHistory(student);
        printCalculations(student);
        System.out.println();

        System.out.println("ТЕСТ 4: Третий семестр");
        student.moveToNextSemester();
        student.addAcademicRecord(new AcademicRecord(Semester.THIRD_SEMESTER,
                "Операционные сети", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.THIRD_SEMESTER,
                "ООП", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.THIRD_SEMESTER,
                "Модели вычислений", RecordType.DIFF_CREDIT, Grade.EXCELLENT));
        printAcademicHistory(student);
        printCalculations(student);

        if (student.budgetPossibility()) {
            System.out.println("Студент может перевестись на бюджет!");
            student.setForm(EducationForm.BUDGET);
            System.out.println("Форма обучения изменена на: " + student.getEducationForm().getForm());
        }
        System.out.println();

        // Тест 5: Четвертый семестр - проверка красного диплома и стипендии
        System.out.println("ТЕСТ 5: Четвертый семестр - проверка диплома и стипендии");
        student.moveToNextSemester();
        student.addAcademicRecord(new AcademicRecord(Semester.FOURTH_SEMESTER,
                "Введение в ИИ", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.FOURTH_SEMESTER,
                "Дифференциальные уравнения", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.FOURTH_SEMESTER,
                "Теория вероятности", RecordType.DIFF_CREDIT, Grade.EXCELLENT));

        student.setThesisGrade(Grade.EXCELLENT);

        printAcademicHistory(student);
        printCalculations(student);

        if (student.increasedScholarshipPossibility()) {
            System.out.println("Студент получает повышенную стипендию в этом семестре!");
        } else {
            System.out.println("Повышенная стипендия не доступна в этом семестре");
        }
        System.out.println();

    }

    private static void printStudentInfo(Student student) {
        System.out.println("Студент: " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Зачетка: " + student.getStudentId());
        System.out.println("Текущий семестр: " + student.getCurrentSemester().getDescription());
        System.out.println("Форма обучения: " + student.getEducationForm().getForm());
        System.out.println("Оценка за диплом: " +
                (student.getThesisGrade() != Grade.UNDEFINED ?
                        student.getThesisGrade().getDescription() : "еще не защищен"));
    }

    private static void printAcademicHistory(Student student) {
        System.out.println("История оценок:");
        List<AcademicRecord> history = student.getAcademicHistory();
        for (AcademicRecord record : history) {
            System.out.printf("  %s: %s (%s) - %s\n",
                    record.getSemester().getDescription(),
                    record.getDisciplineName(),
                    record.getRecordType().toString(),
                    record.getGrade().getDescription());
        }
    }

    private static void printCalculations(Student student) {
        System.out.println("Результаты расчетов:");
        System.out.printf("  Средний балл за все время: %.2f\n", student.getAverageScore());
        System.out.printf("  Средний балл за текущий семестр: %.2f\n",
                student.getAverageScore(student.getCurrentSemester()));
        System.out.println("  Возможность перевода на бюджет: " +
                (student.budgetPossibility() ? "ДА" : "НЕТ"));
        System.out.println("  Возможность красного диплома: " +
                (student.redDiplomaPossibility() ? "ДА" : "НЕТ"));
        System.out.println("  Повышенная стипендия: " +
                (student.increasedScholarshipPossibility() ? "ДА" : "НЕТ"));
    }
}