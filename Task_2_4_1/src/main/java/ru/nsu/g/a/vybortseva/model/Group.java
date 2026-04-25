package ru.nsu.g.a.vybortseva.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final int number;
    private List<Student> group = new ArrayList<>();


    public Group(int number) {
        this.number = number;
    }

    public List<Student> getGroup() {
        return group;
    }

    public void addStudent(Student student) {
        this.group.add(student);
    }

    public void removeStudent(Student student) {
        this.group.remove(student);
    }

    public int getNumber() {
        return number;
    }
}
