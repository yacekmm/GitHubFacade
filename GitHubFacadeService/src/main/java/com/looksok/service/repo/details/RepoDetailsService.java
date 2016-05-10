package com.looksok.service.repo.details;

import com.looksok.service.rest.RestTemplatePrototype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

@Component
public class RepoDetailsService {

    private RestTemplatePrototype restTemplatePrototype;

    public Optional<ResponseEntity<RepoDetailsDto>> requestRepoDetails(String ownerUsername, String repoName){
        System.out.println(restTemplatePrototype);
        throw new NotImplementedException();
    }

}
