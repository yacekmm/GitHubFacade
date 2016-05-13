package com.looksok.controller;

import com.google.common.base.Strings;
import com.looksok.service.repo.details.RepoDetailsService;
import com.looksok.service.repo.details.exception.ErrorMessage;
import com.looksok.service.repo.details.exception.GitHubUnavailableException;
import com.looksok.service.repo.details.exception.RepoNotFoundException;
import com.looksok.service.repo.details.model.RepoDetailsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

@RestController
public final class RepoController {

    private RepoDetailsService repoDetailsService;

    private Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public RepoController(final RepoDetailsService repoDetailsService) {
        this.repoDetailsService = repoDetailsService;
    }

    @RequestMapping(value = "/repositories/{owner}/{repository-name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RepoDetailsModel> getRepoDetails(
            @PathVariable(value = "owner") final String owner,
            @PathVariable(value = "repository-name") final String repoName) {

        if (Strings.isNullOrEmpty(owner) || Strings.isNullOrEmpty(repoName)) {
            throw new IllegalArgumentException("user / repo params must not be null or empty");
        }

        Optional<RepoDetailsModel> result = repoDetailsService.requestRepoDetails(owner, repoName);
        return new ResponseEntity<>(
                result.orElseThrow(
                        () -> new GitHubUnavailableException("Error getting repo details. GitHub unavailable")),
                HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> repoNotFoundExceptionHandler(RepoNotFoundException exception) {
        log.info("Requested repository was not found: " + exception.getMessage());
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> illegalArgumentExceptionHandler(IllegalArgumentException exception){
        log.info("Provided user / repo params are invalid: " + exception.getMessage());
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> gitHubUnavailableExceptionHandler(GitHubUnavailableException exception){
        log.error("GitHub unavailable: " + exception.getMessage());
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
