package com.looksok.service.repo.details.model;

public class RepoDetails {

    private String fullName;
    private String description;
    private String cloneUri;
    private int stars;
    private String createdAt;

    @SuppressWarnings("unused")
//    private RepoDetails() {
//        used for Spring parser only
//    }

    public static RepoDetails fromGitHubModel(GitHubRepoModelSimple gitHubModel) {
        return new RepoDetails(gitHubModel.getFull_name(), gitHubModel.getDescription(),
                gitHubModel.getClone_url(), gitHubModel.getStars(), gitHubModel.getCreated_at());
    }

    private RepoDetails(String fullName, String description, String cloneUri, int stars, String createdAt) {
        this.fullName = fullName;
        this.description = description;
        this.cloneUri = cloneUri;
        this.stars = stars;
        this.createdAt = createdAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepoDetails that = (RepoDetails) o;

        if (stars != that.stars) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (cloneUri != null ? !cloneUri.equals(that.cloneUri) : that.cloneUri != null) return false;
        return createdAt != null ? createdAt.equals(that.createdAt) : that.createdAt == null;

    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (cloneUri != null ? cloneUri.hashCode() : 0);
        result = 31 * result + stars;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

}
