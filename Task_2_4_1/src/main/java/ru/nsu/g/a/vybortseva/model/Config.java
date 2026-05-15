package ru.nsu.g.a.vybortseva.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Root configuration object that stores all data parsed from the DSL.
 */
public class Config {
    private List<Task> tasks = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<CheckPoint> points = new ArrayList<>();
    private List<Assignment> assignments = new ArrayList<>();
    private List<Bonus> bonuses = new ArrayList<>();

    /**
     * Return list of defined tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Return list of student groups.
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Return list of course checkpoints.
     */
    public List<CheckPoint> getPoints() {
        return points;
    }

    /**
     * Return list of task assignments.
     */
    public List<Assignment> getAssignments() {
        return assignments;
    }

    /**
     * Return list of individual student bonuses.
     */
    public List<Bonus> getBonuses() {
        return bonuses;
    }

    /**
     * Retrieves task IDs assigned to a specific group.
     */
    public List<String> getAssignmentsForGroup(int groupNumber) {
        return assignments.stream()
                .filter(a -> a.getGroupNumber() == groupNumber)
                .flatMap(a -> a.getTaskIds().stream())
                .toList();
    }
}
