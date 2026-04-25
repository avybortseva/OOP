package ru.nsu.g.a.vybortseva.model;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private List<Task> tasks = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<CheckPoint> points = new ArrayList<>();
    private List<Assignment> assignments = new ArrayList<>();
    private List<Bonus> bonuses = new ArrayList<>();

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<CheckPoint> getPoints() {
        return points;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public List<Bonus> getBonuses() {
        return bonuses;
    }

    public List<String> getAssignmentsForGroup(int groupNumber) {
        return assignments.stream()
                .filter(a -> a.getGroupNumber() == groupNumber)
                .flatMap(a -> a.getTaskIds().stream())
                .toList();
    }
}
