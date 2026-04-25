package ru.nsu.g.a.vybortseva.model;

public class Student {
    private final String name;
    private final String gitName;
    private final String repoUrl;


    public Student(String name, String gitName, String repoUrl) {
        this.name = name;
        this.gitName = gitName;
        this.repoUrl = repoUrl;
    }

    public String getName() {
        return name;
    }

    public String getGitName() {
        return gitName;
    }

    public String getRepoUrl() {
        return repoUrl;
    }
}
