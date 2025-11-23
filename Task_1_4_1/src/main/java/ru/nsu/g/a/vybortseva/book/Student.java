package ru.nsu.g.a.vybortseva.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Student {
    private final String firstName;
    private final String lastName;
    private final Integer studentId;
    private Semester curSemester;
    private EducationForm form;
    private Grade thesisGrade;
    private List<AcademicRecord> academicHistory;

    public Student(String firstName, String lastName, Integer studentId,
                   EducationForm form) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.curSemester = Semester.FIRST_SEMESTER;
        this.form = form;
        this.thesisGrade = Grade.UNDEFINED;
        this.academicHistory = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Semester getCurrentSemester() {
        return curSemester;
    }

    public EducationForm getEducationForm() {
        return form;
    }

    public Grade getThesisGrade() {
        return thesisGrade;
    }

    public List<AcademicRecord> getAcademicHistory() {
        return new ArrayList<>(academicHistory);
    }

    public void setForm(EducationForm form) {
        this.form = form;
    }

    public void addAcademicRecord(AcademicRecord record) {
        this.academicHistory.add(record);
    }

    public void setThesisGrade(Grade thesisGrade) {
        this.thesisGrade = thesisGrade;
    }

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

    public float getAverageScore() {
        float averageScore = 0;
        float disciplineCount = 0;
        for (AcademicRecord discipline: academicHistory) {
            float curGrade = discipline.getGrade().getNumericValue();
            if (curGrade != -1) {
                averageScore += curGrade;
                disciplineCount += 1;
            }
        }
        return disciplineCount != 0 ? averageScore/disciplineCount : 0;
    }

    public float getAverageScore(Semester semester) {
        float averageScore = 0;
        float disciplineCount = 0;
        for (AcademicRecord discipline: academicHistory) {
            float curGrade = discipline.getGrade().getNumericValue();
            if (semester == discipline.getSemester() && curGrade != -1) {
                averageScore += curGrade;
                disciplineCount += 1;
            }
        }
        return disciplineCount != 0 ? averageScore / disciplineCount : 0;
    }

    public boolean increasedScholarshipPossibility() {
        if (form != EducationForm.BUDGET) {
            return false;
        }

        List<AcademicRecord> currentSemesterRecords = academicHistory.stream()
                .filter(record -> record.getSemester() == curSemester)
                .filter(record -> record.getRecordType() == RecordType.EXAM ||
                        record.getRecordType() == RecordType.DIFF_CREDIT)
                .collect(Collectors.toList());

        if (currentSemesterRecords.isEmpty()) {
            return false;
        }

        return currentSemesterRecords.stream()
                .allMatch(record -> record.getGrade() == Grade.EXCELLENT);
    }

    public boolean redDiplomaPossibility() {
        if (thesisGrade != Grade.UNDEFINED && thesisGrade != Grade.EXCELLENT) {
            return false;
        }

        Map<String, AcademicRecord> finalGrades = new HashMap<>();
        for (AcademicRecord record : academicHistory) {
            if (!finalGrades.containsKey(record.getDisciplineName()) ||
                    isLaterSemester(record.getSemester(),
                            finalGrades.get(record.getDisciplineName()).getSemester())) {
                finalGrades.put(record.getDisciplineName(), record);
            }
        }

        List<AcademicRecord> gradedRecords = finalGrades.values().stream()
                .filter(record -> record.getRecordType() == RecordType.EXAM ||
                        record.getRecordType() == RecordType.DIFF_CREDIT)
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

        for (Semester semester : lastTwoSessions) {
            boolean hasSatisfactoryInExams = academicHistory.stream()
                    .filter(record -> record.getSemester() == semester)
                    .filter(record -> record.getRecordType() == RecordType.EXAM)
                    .anyMatch(record -> record.getGrade() == Grade.SATISFACTORY);

            if (hasSatisfactoryInExams) {
                return false;
            }
        }

        return true;
    }
}

