package ru.nsu.g.a.vybortseva.model;

/**
 * Represents a student with their personal information and repository details.
 */
public class Student {
    private final String id;
    private final String name;
    private final String gitName;
    private final String repoUrl;

    /**
     * Constructs a Student with ID, name, Git identifier, and repository URL.
     */
    public Student(String id, String name, String gitName, String repoUrl) {
        this.id = id;
        this.name = name;
        this.gitName = gitName;
        this.repoUrl = repoUrl;
    }

    /**
     * Return the student's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Return the student's full name.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the student's full name.
     */
    public String getFullName() {
        return name;
    }

    /**
     * Return the student's Git username.
     */
    public String getGitName() {
        return gitName;
    }

    /**
     * Return the URL to the student's repository.
     */
    public String getRepoUrl() {
        return repoUrl;
    }
}
