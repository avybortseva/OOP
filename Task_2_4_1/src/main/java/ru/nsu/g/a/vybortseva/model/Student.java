package ru.nsu.g.a.vybortseva.model;

/**
 * Represents a student with their personal information and repository details.
 */
public class Student {
    private final String name;
    private final String gitName;
    private final String repoUrl;

    /**
     * Constructs a Student with name, Git identifier, and repository URL.
     */
    public Student(String name, String gitName, String repoUrl) {
        this.name = name;
        this.gitName = gitName;
        this.repoUrl = repoUrl;
    }

    /**
     * Return the student's full name.
     */
    public String getName() {
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
