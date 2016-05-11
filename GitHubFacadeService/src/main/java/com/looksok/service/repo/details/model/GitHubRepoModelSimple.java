package com.looksok.service.repo.details.model;

public class GitHubRepoModelSimple {

    private String full_name;
    private String description;
    private String clone_url;
    private int stargazers_count;
    private String created_at;

    @SuppressWarnings("unused")
    public GitHubRepoModelSimple() {
        //used for Spring parser only
    }

    public String getFull_name() {
        return full_name;
    }

    public String getDescription() {
        return description;
    }

    public String getClone_url() {
        return clone_url;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "RepoDetails{" +
                "fullName='" + full_name + '\'' +
                ", description='" + description + '\'' +
                ", cloneUri='" + clone_url + '\'' +
                ", stars='" + stargazers_count + '\'' +
                ", createdAt='" + created_at + '\'' +
                '}';
    }
}
