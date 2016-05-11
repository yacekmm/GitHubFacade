package com.looksok.service.repo.details.exception;

public class RepoNotFoundException extends RuntimeException {

    public RepoNotFoundException(String message) {
        super(message);
    }
}
