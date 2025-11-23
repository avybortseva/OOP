package ru.nsu.g.a.vybortseva.book;

/**
 * Represents an academic record for a student in a specific semester.
 * Contains information about the discipline, type of assessment, and grade.
 */
public class AcademicRecord {
    private final Semester semester;
    private final String disciplineName;
    private final RecordType recordType;
    private final Grade grade;

    /**
     * Creates a new academic record with all required information.
     */
    public AcademicRecord(Semester semester, String disciplineName,
                          RecordType recordType, Grade grade) {
        this.semester = semester;
        this.disciplineName = disciplineName;
        this.recordType = recordType;
        this.grade = grade;
    }

    /**
     * Returns the name of the academic discipline.
     */
    public String getDisciplineName() {
        return disciplineName;
    }

    /**
     * Returns the semester when the assessment was taken.
     */
    public Semester getSemester() {
        return semester;
    }

    /**
     * Returns the type of academic assessment.
     */
    public RecordType getRecordType() {
        return recordType;
    }

    /**
     * Returns the grade received for the assessment.
     */
    public Grade getGrade() {
        return grade;
    }
}

