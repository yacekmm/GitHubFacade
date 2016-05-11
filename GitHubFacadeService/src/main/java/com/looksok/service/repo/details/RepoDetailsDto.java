package com.looksok.service.repo.details;

public class RepoDetailsDto {

    private String fullName;
    private String description;
    private String cloneUri;
    private String stars;
    private String createdAt;

    @SuppressWarnings("unused")
    public RepoDetailsDto() {
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

    public String getStars() {
        return stars;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "RepoDetailsDto{" +
                "fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                ", cloneUri='" + cloneUri + '\'' +
                ", stars='" + stars + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
