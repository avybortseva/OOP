package ru.nsu.g.a.vybortseva.model;

import java.util.ArrayList;
import java.util.List;

public class Assignment {
    private final int groupNumber;
    private final List<String> taskIds = new ArrayList<>();

    public Assignment(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public void addTask(String taskId) {
        this.taskIds.add(taskId);
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public List<String> getTaskIds() {
        return taskIds;
    }
}
