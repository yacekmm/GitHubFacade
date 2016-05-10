package com.looksok.controller;

import com.looksok.service.RepoDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepoController {

    private RepoDetailsService repoDetailsService;

    @Autowired
    public RepoController(RepoDetailsService repoDetailsService) {
        this.repoDetailsService = repoDetailsService;
    }

    public void getRepoDetails(){
        repoDetailsService.requestRepoDetails();
    }
}
