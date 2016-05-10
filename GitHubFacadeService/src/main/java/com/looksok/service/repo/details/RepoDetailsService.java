package com.looksok.service.repo.details;

import com.looksok.constants.ConstAppLogic;
import com.looksok.service.rest.RestTemplatePrototype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
public class RepoDetailsService {

    private RestTemplatePrototype restTemplatePrototype;

    @Autowired
    public RepoDetailsService(RestTemplatePrototype restTemplatePrototype) {
        this.restTemplatePrototype = restTemplatePrototype;
    }

    public Optional<ResponseEntity<RepoDetailsDto>> requestRepoDetails(String ownerUsername, String repoName){
        URI targetUrl = UriComponentsBuilder.fromUriString(ConstAppLogic.GitHubUrl.REPOS)
                .path(ownerUsername)
                .path("/")
                .path(repoName)
                .build(true).toUri();

        restTemplatePrototype.getRestTemplate().getForObject(targetUrl, String.class);
        return Optional.empty();
    }

}
