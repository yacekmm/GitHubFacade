package com.looksok.service.repo.details;

import com.looksok.constants.ConstAppLogic;
import com.looksok.service.repo.details.model.GitHubRepoModelSimple;
import com.looksok.service.repo.details.model.RepoDetails;
import com.looksok.service.rest.RestTemplatePrototype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.Optional;

@Component
public class RepoDetailsService {

    private RestTemplatePrototype restTemplatePrototype;

    private Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public RepoDetailsService(RestTemplatePrototype restTemplatePrototype) {
        this.restTemplatePrototype = restTemplatePrototype;
    }

    /**
     * @throws RepoNotFoundException when user/repo pair does not exist
     */
    public Optional<RepoDetails> requestRepoDetails(String ownerUsername, String repoName){

        URI targetUrl = UriComponentsBuilder.fromUriString(ConstAppLogic.GitHubUrl.REPOS)
                .path(ownerUsername)
                .path("/")
                .path(repoName)
                .build(true).toUri();

        try{
            ResponseEntity<GitHubRepoModelSimple> result = restTemplatePrototype.getRestTemplate()
                    .getForEntity(targetUrl, GitHubRepoModelSimple.class);
            System.out.println("RESULT: " + result);
            return Optional.of(RepoDetails.fromGitHubModel(result.getBody()));
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                log.info("Repo for user [" + ownerUsername + "] repoName [" + repoName + "] was not found");
                throw new RepoNotFoundException(e.getMessage());
            }else{
                log.info("Http Client exception occured. Returning empty result");
                return Optional.empty();
            }
        } catch(RestClientException e){
            log.info("Client exception occured. Returning empty result");
            return Optional.empty();
        }
    }

}
