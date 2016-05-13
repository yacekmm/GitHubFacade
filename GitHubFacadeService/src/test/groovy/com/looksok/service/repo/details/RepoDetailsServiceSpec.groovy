package com.looksok.service.repo.details

import com.looksok.constants.ConstAppConfig
import com.looksok.service.repo.details.exception.RepoNotFoundException
import com.looksok.service.repo.details.model.GitHubRepoModelSimple
import com.looksok.service.repo.details.model.RepoDetailsModel
import com.looksok.service.rest.RestTemplateSingleton
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class RepoDetailsServiceSpec extends Specification {

    private RepoDetailsService repoDetailsService
    private RestTemplateSingleton restTemplatePrototypeMock
    private RestTemplate restTemplateMock

    void setup() {
        restTemplatePrototypeMock = Mock()
        restTemplateMock = Mock()
        repoDetailsService = new RepoDetailsService(restTemplatePrototypeMock)

        restTemplatePrototypeMock.getInstance() >> restTemplateMock
    }

    def "RequestRepoDetails executes GET on restTemplate"() {
        given:
        String expectedUser = "user"
        String expectedRepo = "repo"
        def expectedUri = new URI(ConstAppConfig.GitHub.URL_REPOS + expectedUser + "/" + expectedRepo)

        when:
        repoDetailsService.requestRepoDetails(expectedUser, expectedRepo)

        then:
        1 * restTemplatePrototypeMock.getInstance() >> restTemplateMock
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
        def responseEntityStub = Stub(ResponseEntity)
        def gitHubModelStub = Stub(GitHubRepoModelSimple)
        responseEntityStub.getBody() >> gitHubModelStub;
        gitHubModelStub.getCreated_at() >> "2016-04-05T16:39:50Z"
        restTemplateMock.exchange(_, _, _, GitHubRepoModelSimple.class) >> responseEntityStub

        when:
        def repoDetails = repoDetailsService.requestRepoDetails("anyUser", "anyRepo")

        then:
        repoDetails.isPresent()
        repoDetails.get() == RepoDetailsModel.fromGitHubModel(gitHubModelStub)
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
