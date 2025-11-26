package ru.nsu.g.a.vybortseva.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditBookTest {

    private CreditBook student;
    private CreditBook paidStudent;

    @BeforeEach
    void setUp() {
        student = new CreditBook("Иван", "Иванов", 12345, EducationForm.BUDGET);
        paidStudent = new CreditBook("Петр", "Петров", 54321, EducationForm.PAID);
    }

    @Test
    void getFirstName() {
        assertEquals("Иван", student.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Иванов", student.getLastName());
    }

    @Test
    void getStudentId() {
        assertEquals(12345, student.getStudentId());
    }

    @Test
    void getCurrentSemester() {
        assertEquals(Semester.FIRST_SEMESTER, student.getCurrentSemester());
    }

    @Test
    void getEducationForm() {
        assertEquals(EducationForm.BUDGET, student.getEducationForm());
        assertEquals(EducationForm.PAID, paidStudent.getEducationForm());
    }

    @Test
    void getThesisGrade() {
        assertEquals(Grade.UNDEFINED, student.getThesisGrade());
    }

    @Test
    void getAcademicHistory() {
        assertTrue(student.getAcademicHistory().isEmpty());

        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));
        assertEquals(1, student.getAcademicHistory().size());
    }

    @Test
    void setForm() {
        student.setForm(EducationForm.PAID);
        assertEquals(EducationForm.PAID, student.getEducationForm());
    }

    @Test
    void addAcademicRecord() {
        AcademicRecord record = new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT);
        student.addAcademicRecord(record);

        List<AcademicRecord> history = student.getAcademicHistory();
        assertEquals(1, history.size());
        assertEquals("Математика", history.get(0).getDisciplineName());
        assertEquals(Grade.EXCELLENT, history.get(0).getGrade());
    }

    @Test
    void setThesisGrade() {
        student.setThesisGrade(Grade.EXCELLENT);
        assertEquals(Grade.EXCELLENT, student.getThesisGrade());
    }

    @Test
    void moveToNextSemester() {
        assertEquals(Semester.FIRST_SEMESTER, student.getCurrentSemester());

        student.moveToNextSemester();
        assertEquals(Semester.SECOND_SEMESTER, student.getCurrentSemester());

        student.moveToNextSemester();
        assertEquals(Semester.THIRD_SEMESTER, student.getCurrentSemester());
    }

    @Test
    void getAverageScore() {
        assertEquals(0.0, student.getAverageScore());
    }

    @Test
    void testGetAverageScore() {
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Физика", RecordType.EXAM, Grade.GOOD));
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "История", RecordType.DIFF_CREDIT, Grade.SATISFACTORY));

        assertEquals(4.0, student.getAverageScore(), 0.01);
    }

    @Test
    void increasedScholarshipPossibility_BudgetStudentAllExcellent() {
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Физика", RecordType.DIFF_CREDIT, Grade.EXCELLENT));

        assertTrue(student.increasedScholarshipPossibility());
    }

    @Test
    void increasedScholarshipPossibility_BudgetStudentWithGoodGrade() {
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Физика", RecordType.DIFF_CREDIT, Grade.GOOD));

        assertFalse(student.increasedScholarshipPossibility());
    }

    @Test
    void increasedScholarshipPossibility_PaidStudent() {
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));

        assertFalse(paidStudent.increasedScholarshipPossibility());
    }

    @Test
    void redDiplomaPossibility_AllConditionsMet() {
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Физика", RecordType.DIFF_CREDIT, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.SECOND_SEMESTER,
                "Информатика", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.SECOND_SEMESTER,
                "Алгоритмы", RecordType.DIFF_CREDIT, Grade.EXCELLENT));

        student.setThesisGrade(Grade.EXCELLENT);
        assertTrue(student.redDiplomaPossibility());
    }

    @Test
    void redDiplomaPossibility_WithSatisfactoryGrade() {
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Физика", RecordType.DIFF_CREDIT, Grade.SATISFACTORY));
        student.setThesisGrade(Grade.EXCELLENT);

        assertFalse(student.redDiplomaPossibility());
    }

    @Test
    void redDiplomaPossibility_ThesisNotExcellent() {
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));
        student.setThesisGrade(Grade.GOOD);

        assertFalse(student.redDiplomaPossibility());
    }

    @Test
    void redDiplomaPossibility_WithRetake() {
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.GOOD));
        student.addAcademicRecord(new AcademicRecord(Semester.SECOND_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));
        student.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Физика", RecordType.EXAM, Grade.EXCELLENT));
        student.setThesisGrade(Grade.EXCELLENT);

        assertTrue(student.redDiplomaPossibility());
    }

    @Test
    void budgetPossibility_PaidStudentNoSatisfactory() {
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Физика", RecordType.EXAM, Grade.GOOD));
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.SECOND_SEMESTER,
                "Информатика", RecordType.EXAM, Grade.EXCELLENT));
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.SECOND_SEMESTER,
                "Алгоритмы", RecordType.EXAM, Grade.GOOD));

        assertTrue(paidStudent.budgetPossibility());
    }

    @Test
    void budgetPossibility_PaidStudentWithSatisfactory() {
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Физика", RecordType.EXAM, Grade.SATISFACTORY));
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.SECOND_SEMESTER,
                "Информатика", RecordType.EXAM, Grade.EXCELLENT));

        assertFalse(paidStudent.budgetPossibility());
    }

    @Test
    void budgetPossibility_BudgetStudent() {
        assertFalse(student.budgetPossibility());
    }

    @Test
    void budgetPossibility_LessThanTwoSessions() {
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));
        assertFalse(paidStudent.budgetPossibility());
    }

    @Test
    void budgetPossibility_SatisfactoryInDiffCreditAllowed() {
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Математика", RecordType.EXAM, Grade.EXCELLENT));
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.FIRST_SEMESTER,
                "Физкультура", RecordType.DIFF_CREDIT, Grade.SATISFACTORY));
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.SECOND_SEMESTER,
                "Информатика", RecordType.EXAM, Grade.EXCELLENT));
        paidStudent.addAcademicRecord(new AcademicRecord(Semester.SECOND_SEMESTER,
                "ОБЖ", RecordType.DIFF_CREDIT, Grade.SATISFACTORY));

        assertTrue(paidStudent.budgetPossibility());
    }
}