package com.looksok.service.repo.details.model;

public class GitHubRepoModelSimple {

    private String fullName;
    private String description;
    private String cloneUri;
    private int stars;
    private String createdAt;

    @SuppressWarnings("unused")
    public GitHubRepoModelSimple() {
        //used for Spring parser only
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public String getCloneUri() {
        return cloneUri;
    }

    public int getStars() {
        return stars;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "RepoDetails{" +
                "fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                ", cloneUri='" + cloneUri + '\'' +
                ", stars='" + stars + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
