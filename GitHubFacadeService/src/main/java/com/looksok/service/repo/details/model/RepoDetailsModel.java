package com.looksok.service.repo.details.model;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.TimeZone;

public class RepoDetailsModel {

    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    private ZonedDateTime createdAt;

    public static RepoDetailsModel fromGitHubModel(final GitHubRepoModelSimple gitHubModel) {
        Instant instant = Instant.parse(gitHubModel.getCreated_at());
        return new RepoDetailsModel(gitHubModel.getFull_name(), gitHubModel.getDescription(),
                gitHubModel.getClone_url(), gitHubModel.getStargazers_count(), ZonedDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId()));
    }

    private RepoDetailsModel(String fullName, String description, String cloneUrl, int stars, ZonedDateTime createdAt) {
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
        return createdAt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    @Override
    public String toString() {
        return "RepoDetailsModel{" +
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

        RepoDetailsModel that = (RepoDetailsModel) o;

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
