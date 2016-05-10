package com.looksok.controller;

import com.looksok.service.repo.details.RepoDetailsService;
import com.looksok.service.repo.details.RepoDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepoController {

    private RepoDetailsService repoDetailsService;

    @Autowired
    public RepoController(RepoDetailsService repoDetailsService) {
        this.repoDetailsService = repoDetailsService;
    }

    public ResponseEntity<RepoDetailsDto> getRepoDetails(String owner, String repoName){
        return repoDetailsService.requestRepoDetails(owner, repoName);
    }
}
