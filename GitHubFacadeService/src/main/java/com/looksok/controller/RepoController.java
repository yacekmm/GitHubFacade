package com.looksok.controller;

import com.google.common.base.Strings;
import com.looksok.service.repo.details.RepoDetailsService;
import com.looksok.service.repo.details.exception.RepoNotFoundException;
import com.looksok.service.repo.details.model.RepoDetailsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
            log.info("BadRequest: params must not be null or empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<RepoDetailsModel> result = repoDetailsService.requestRepoDetails(owner, repoName);

            if (result.isPresent()) {
                return new ResponseEntity<>(result.get(), HttpStatus.OK);
            } else {
                log.error("Error getting repo details. GitHub unavailable");
                return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
            }
        } catch (RepoNotFoundException e) {
            log.info("Requested repository was not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e){
            log.info("repo user / repo name params are invalid: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
