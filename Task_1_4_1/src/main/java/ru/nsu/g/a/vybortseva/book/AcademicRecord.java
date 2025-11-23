package ru.nsu.g.a.vybortseva.book;

public class AcademicRecord {
    private final Semester semester;
    private final String disciplineName;
    private final RecordType recordType;
    private final Grade grade;

    public AcademicRecord(Semester semester, String disciplineName,
                          RecordType recordType, Grade grade) {
        this.semester = semester;
        this.disciplineName = disciplineName;
        this.recordType = recordType;
        this.grade = grade;
    }


    public String getDisciplineName() {
        return disciplineName;
    }

    public Semester getSemester() {
        return semester;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public Grade getGrade() {
        return grade;
    }
}

