package com.looksok.service.repo.details;

public class RepoNotFoundException extends RuntimeException {

    public RepoNotFoundException(String message) {
        super(message);
    }
}
