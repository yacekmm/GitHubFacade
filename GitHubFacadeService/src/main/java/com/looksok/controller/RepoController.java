package com.looksok.controller;

import com.google.common.base.Strings;
import com.looksok.service.repo.details.RepoDetailsService;
import com.looksok.service.repo.details.RepoDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

@RestController
public class RepoController {

    private Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private RepoDetailsService repoDetailsService;

    @Autowired
    public RepoController(RepoDetailsService repoDetailsService) {
        this.repoDetailsService = repoDetailsService;
    }

    public ResponseEntity<RepoDetailsDto> getRepoDetails(String owner, String repoName){
        if(Strings.isNullOrEmpty(owner) || Strings.isNullOrEmpty(repoName)){
            log.info("BadRequest: params must not be null or empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return repoDetailsService.requestRepoDetails(owner, repoName);
    }
}
