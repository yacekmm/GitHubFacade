package com.looksok.service.repo.details.exception;

public class RepoNotFoundException extends RuntimeException {

    public RepoNotFoundException(final String message) {
        super(message);
    }
}
