package ru.nsu.g.a.vybortseva.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a student's electronic grade book.
 * Manages academic records, calculates statistics, and checks
 * eligibility for various academic benefits.
 */
public class CreditBook {
    private final String firstName;
    private final String lastName;
    private final Integer studentId;
    private Semester curSemester;
    private EducationForm form;
    private Grade thesisGrade;
    private final List<AcademicRecord> academicHistory;

    /**
     * Creates a new student with basic information and initializes academic history.
     */
    public CreditBook(String firstName, String lastName, Integer studentId,
                      EducationForm form) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.curSemester = Semester.FIRST_SEMESTER;
        this.form = form;
        this.thesisGrade = Grade.UNDEFINED;
        this.academicHistory = new ArrayList<>();
    }

    /**
     * Returns the student's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the student's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the student's unique identifier.
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * Returns the current semester of the student.
     */
    public Semester getCurrentSemester() {
        return curSemester;
    }

    /**
     * Returns the current education form of the student.
     */
    public EducationForm getEducationForm() {
        return form;
    }

    /**
     * Returns the thesis grade or undefined if not yet assessed.
     */
    public Grade getThesisGrade() {
        return thesisGrade;
    }

    /**
     * Returns a copy of the student's academic history.
     */
    public List<AcademicRecord> getAcademicHistory() {
        return new ArrayList<>(academicHistory);
    }

    /**
     * Updates the student's education form.
     */
    public void setForm(EducationForm form) {
        this.form = form;
    }

    /**
     * Adds a new academic record to the student's history.
     */
    public void addAcademicRecord(AcademicRecord record) {
        this.academicHistory.add(record);
    }

    /**
     * Sets the grade for the student's qualification thesis.
     */
    public void setThesisGrade(Grade thesisGrade) {
        this.thesisGrade = thesisGrade;
    }

    /**
     * Advances the student to the next semester in sequence.
     */
    public void moveToNextSemester() {
        int nextNumber = curSemester.getNumber() + 1;
        Semester nextSemester = getSemesterByNumber(nextNumber);
        if (nextSemester != null) {
            this.curSemester = nextSemester;
        }
    }

    private Semester getSemesterByNumber(int number) {
        for (Semester semester : Semester.values()) {
            if (semester.getNumber() == number) {
                return semester;
            }
        }
        return null;
    }

    /**
     * Calculates the average score across all academic records.
     */
    public float getAverageScore() {
        return (float) academicHistory.stream()
                .map(discipline -> discipline.getGrade().getNumericValue())
                .filter(grade -> grade != -1)
                .mapToDouble(grade -> grade)
                .average()
                .orElse(0.0);
    }

    /**
     * Calculates the average score for a specific semester.
     */
    public float getAverageScore(Semester semester) {
        return (float) academicHistory.stream()
                .filter(discipline -> discipline.getSemester() == semester)
                .mapToDouble(discipline -> discipline.getGrade().getNumericValue())
                .filter(grade -> grade != -1)
                .average()
                .orElse(0.0);
    }

    /**
     * Checks eligibility for increased scholarship in the current semester.
     */
    public boolean increasedScholarshipPossibility() {
        if (form != EducationForm.BUDGET) {
            return false;
        }
        List<AcademicRecord> currentSemesterRecords = academicHistory.stream()
                .filter(record -> record.getSemester() == curSemester)
                .filter(record -> record.getRecordType() == RecordType.EXAM
                        || record.getRecordType() == RecordType.DIFF_CREDIT)
                .toList();

        if (currentSemesterRecords.isEmpty()) {
            return false;
        }
        return currentSemesterRecords.stream()
                .allMatch(record -> record.getGrade() == Grade.EXCELLENT);
    }

    /**
     * Determines if the student can potentially receive a honors diploma.
     */
    public boolean redDiplomaPossibility() {
        if (thesisGrade != Grade.UNDEFINED && thesisGrade != Grade.EXCELLENT) {
            return false;
        }

        Map<String, AcademicRecord> finalGrades = academicHistory.stream()
                .collect(Collectors.toMap(
                        AcademicRecord::getDisciplineName,
                        record -> record,
                        (existing, current) ->
                                isLaterSemester(current.getSemester(), existing.getSemester())
                                ? current
                                : existing
                ));

        List<AcademicRecord> gradedRecords = finalGrades.values().stream()
                .filter(record -> record.getRecordType() == RecordType.EXAM
                        || record.getRecordType() == RecordType.DIFF_CREDIT)
                .toList();

        if (gradedRecords.isEmpty()) {
            return false;
        }

        long excellentCount = gradedRecords.stream()
                .filter(record -> record.getGrade() == Grade.EXCELLENT)
                .count();

        float excellentPercentage = (float) excellentCount / gradedRecords.size() * 100;

        boolean hasSatisfactory = gradedRecords.stream()
                .anyMatch(record -> record.getGrade() == Grade.SATISFACTORY);

        return excellentPercentage >= 75 && !hasSatisfactory;
    }

    private boolean isLaterSemester(Semester sem1, Semester sem2) {
        return sem1.getNumber() > sem2.getNumber();
    }

    /**
     * Checks if the student is eligible to transfer from paid to budget education form.
     */
    public boolean budgetPossibility() {
        if (form != EducationForm.PAID) {
            return false;
        }

        List<Semester> allSemesters = academicHistory.stream()
                .filter(record -> record.getRecordType() == RecordType.EXAM)
                .map(AcademicRecord::getSemester)
                .distinct()
                .sorted((s1, s2) -> Integer.compare(s2.getNumber(), s1.getNumber()))
                .toList();

        if (allSemesters.size() < 2) {
            return false;
        }

        List<Semester> lastTwoSessions = allSemesters.subList(0, Math.min(2, allSemesters.size()));

        boolean hasSatisfactoryInLastTwoSessions = lastTwoSessions.stream()
                .anyMatch(semester -> academicHistory.stream()
                        .filter(record -> record.getSemester() == semester)
                        .filter(record -> record.getRecordType() == RecordType.EXAM)
                        .anyMatch(record -> record.getGrade() == Grade.SATISFACTORY)
                );

        if (hasSatisfactoryInLastTwoSessions) {
            return false;
        }

        return true;
    }
}
