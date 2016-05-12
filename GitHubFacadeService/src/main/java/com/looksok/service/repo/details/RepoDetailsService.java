package com.looksok.service.repo.details;

import com.looksok.constants.ConstAppLogic;
import com.looksok.service.repo.details.exception.RepoNotFoundException;
import com.looksok.service.repo.details.model.GitHubRepoModelSimple;
import com.looksok.service.repo.details.model.RepoDetailsModel;
import com.looksok.service.rest.RestTemplatePrototype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

@Component
public class RepoDetailsService {

    private RestTemplatePrototype restTemplatePrototype;

    private Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public RepoDetailsService(final RestTemplatePrototype restTemplatePrototype) {
        this.restTemplatePrototype = restTemplatePrototype;
    }

    /**
     * @throws RepoNotFoundException when user/repo pair does not exist
     * @throws IllegalArgumentException when owner / repo params are invalid on url creation
     */
    public Optional<RepoDetailsModel> requestRepoDetails(String ownerUsername, String repoName) {


        try {
            ResponseEntity<GitHubRepoModelSimple> result = restTemplatePrototype.getRestTemplate()
                    .exchange(buildTargetUrl(ownerUsername, repoName), HttpMethod.GET, buildHttpEntity(), GitHubRepoModelSimple.class);
            return Optional.of(RepoDetailsModel.fromGitHubModel(result.getBody()));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("Repo for user [" + ownerUsername + "] repoName [" + repoName + "] was not found");
                throw new RepoNotFoundException(e.getMessage());
            } else {
                log.info("HttpClientErrorException occurred (Returning empty result): " + e.getMessage());
                return Optional.empty();
            }
        } catch (RestClientException e) {
            log.info("HttpClientException occurred (Returning empty result): " + e.getMessage());
            return Optional.empty();
        } catch (IllegalArgumentException e){
            log.info("Illegal argument exception in url: " + e.getMessage());
            throw e;
        }
    }

    private HttpEntity<Void> buildHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(ConstAppLogic.GitHub.HEADER_ACCEPT_V3));
        return new HttpEntity<>(null, headers);
    }

    private URI buildTargetUrl(String ownerUsername, String repoName) {
        return UriComponentsBuilder.fromUriString(ConstAppLogic.GitHub.URL_REPOS)
                    .path(ownerUsername).path("/").path(repoName).build(true).toUri();
    }


}
