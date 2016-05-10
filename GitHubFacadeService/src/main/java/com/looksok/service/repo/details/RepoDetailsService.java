package com.looksok.service.repo.details;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

@Component
public class RepoDetailsService {

    public Optional<ResponseEntity<RepoDetailsDto>> requestRepoDetails(String ownerUsername, String repoName){
        throw new NotImplementedException();
    }

}
