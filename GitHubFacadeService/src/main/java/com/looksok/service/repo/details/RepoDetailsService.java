package com.looksok.service.repo.details;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class RepoDetailsService {

    public ResponseEntity<RepoDetailsDto> requestRepoDetails(String ownerUsername, String repoName){
        throw new NotImplementedException();
    }

}
