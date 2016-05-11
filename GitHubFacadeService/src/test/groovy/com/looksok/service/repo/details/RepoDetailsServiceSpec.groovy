package com.looksok.service.repo.details

import com.looksok.constants.ConstAppLogic
import com.looksok.service.repo.details.model.GitHubRepoModelSimple
import com.looksok.service.repo.details.model.RepoDetails
import com.looksok.service.rest.RestTemplatePrototype
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class RepoDetailsServiceSpec extends Specification {

    private RepoDetailsService repoDetailsService
    private RestTemplatePrototype restTemplatePrototypeMock
    private RestTemplate restTemplateMock

    void setup() {
        restTemplatePrototypeMock = Mock()
        restTemplateMock = Mock()
        repoDetailsService = new RepoDetailsService(restTemplatePrototypeMock)

        restTemplatePrototypeMock.getRestTemplate() >> restTemplateMock
    }

    def "RequestRepoDetails executes GET on restTemplate"() {
        given:
        String expectedUser = "user"
        String expectedRepo = "repo"
        def expectedUri = new URI(ConstAppLogic.GitHub.URL_REPOS + expectedUser + "/" + expectedRepo)

        when:
        repoDetailsService.requestRepoDetails(expectedUser, expectedRepo)

        then:
        1 * restTemplatePrototypeMock.getRestTemplate() >> restTemplateMock
        1 * restTemplateMock.exchange(_, _, _, _) >> {actualUrl, method, entity, responseType ->
            assert actualUrl == expectedUri
            assert method == HttpMethod.GET

            def modelSimpleMock = Mock(GitHubRepoModelSimple)
            modelSimpleMock.getCreated_at() >> "2016-04-05T16:39:50Z"
            new ResponseEntity<GitHubRepoModelSimple>(modelSimpleMock, HttpStatus.OK)
        }
    }

    def "RequestRepoDetails returns result as a parsed object"() {
        given:
        def responseEntityMock = Mock(ResponseEntity)
        def gitHubModelMock = Mock(GitHubRepoModelSimple)
        responseEntityMock.getBody() >> gitHubModelMock;
        gitHubModelMock.getCreated_at() >> "2016-04-05T16:39:50Z"
        restTemplateMock.exchange(_, _, _, GitHubRepoModelSimple.class) >> responseEntityMock

        when:
        def repoDetails = repoDetailsService.requestRepoDetails("anyUser", "anyRepo")

        then:
        repoDetails.isPresent()
        repoDetails.get() == RepoDetails.fromGitHubModel(gitHubModelMock)
    }

    def "RequestRepoDetails returns empty optional on RestClientException"() {
        given:
        restTemplateMock.exchange(*_) >> { throw new RestClientException("msg") }

        when:
        def result = repoDetailsService.requestRepoDetails("anyUser", "anyRepo")

        then:
        result.isPresent() == false
    }

    def "RequestRepoDetails throws RepoNotFoundException on unknown user/repo pair"() {
        given:
        restTemplateMock.exchange(*_) >> { throw new HttpClientErrorException(HttpStatus.NOT_FOUND) }

        when:
        repoDetailsService.requestRepoDetails("anyUser", "anyRepo")

        then:
        RepoNotFoundException e = thrown()
    }

    def "RequestRepoDetails returns empty result on other error"() {
        given:
        restTemplateMock.exchange(*_) >> { throw new HttpClientErrorException(HttpStatus.CONFLICT) }

        when:
        def result = repoDetailsService.requestRepoDetails("anyUser", "anyRepo")

        then:
        result.isPresent() == false
    }

    def "RequestRepoDetails Handles illegal characters in path"() {

        when:
        repoDetailsService.requestRepoDetails("anyUser", "????")

        then:
        thrown IllegalArgumentException
    }

}
