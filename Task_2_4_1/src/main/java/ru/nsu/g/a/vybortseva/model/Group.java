package ru.nsu.g.a.vybortseva.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student group containing a collection of students.
 */
public class Group {
    private final int number;
    private List<Student> group = new ArrayList<>();

    /**
     * Constructs a new Group with the specified group number.
     */
    public Group(int number) {
        this.number = number;
    }

    /**
     * Return the list of students belonging to this group.
     */
    public List<Student> getGroup() {
        return group;
    }

    /**
     * Adds a student to the group.
     */
    public void addStudent(Student student) {
        this.group.add(student);
    }

    /**
     * Removes a student from the group.
     */
    public void removeStudent(Student student) {
        this.group.remove(student);
    }

    /**
     * Return the group number.
     */
    public int getNumber() {
        return number;
    }
}
