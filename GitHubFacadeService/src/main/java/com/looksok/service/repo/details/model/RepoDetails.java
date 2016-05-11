package com.looksok.service.repo.details.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class RepoDetails {

    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    private String createdAt;

    public static RepoDetails fromGitHubModel(GitHubRepoModelSimple gitHubModel) {
        System.out.println(gitHubModel.getCreated_at());
        ZonedDateTime parsedDate = ZonedDateTime.parse(gitHubModel.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("parsedDate: " + parsedDate);
        return new RepoDetails(gitHubModel.getFull_name(), gitHubModel.getDescription(),
                gitHubModel.getClone_url(), gitHubModel.getStars(), parsedDate.toString());
    }

    private RepoDetails(String fullName, String description, String cloneUrl, int stars, String createdAt) {
        this.fullName = fullName;
        this.description = description;
        this.cloneUrl = cloneUrl;
        this.stars = stars;
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public String getCloneUrl() {
        return cloneUrl;
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
                ", cloneUri='" + cloneUrl + '\'' +
                ", stars='" + stars + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepoDetails that = (RepoDetails) o;

        if (stars != that.stars) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (cloneUrl != null ? !cloneUrl.equals(that.cloneUrl) : that.cloneUrl != null) return false;
        return createdAt != null ? createdAt.equals(that.createdAt) : that.createdAt == null;

    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (cloneUrl != null ? cloneUrl.hashCode() : 0);
        result = 31 * result + stars;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

}
