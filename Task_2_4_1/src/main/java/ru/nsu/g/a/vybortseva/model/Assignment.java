package ru.nsu.g.a.vybortseva.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the mapping of specific tasks to a student group.
 */
public class Assignment {
    private final int groupNumber;
    private final List<String> taskIds = new ArrayList<>();

    /**
     * Constructs an Assignment for a specific group.
     */
    public Assignment(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    /**
     * Adds a task identifier to this assignment.
     */
    public void addTask(String taskId) {
        this.taskIds.add(taskId);
    }

    /**
     * Return the assigned group number.
     */
    public int getGroupNumber() {
        return groupNumber;
    }

    /**
     * Return the list of assigned task identifiers.
     */
    public List<String> getTaskIds() {
        return taskIds;
    }
}
