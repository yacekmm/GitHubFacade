package com.looksok.controller

import com.looksok.service.repo.details.RepoDetailsService
import com.looksok.service.repo.details.exception.GitHubUnavailableException
import com.looksok.service.repo.details.exception.RepoNotFoundException
import com.looksok.service.repo.details.model.RepoDetailsModel
import org.springframework.http.HttpStatus
import spock.lang.Specification

class RepoControllerSpec extends Specification {

    private RepoController repoController
    private RepoDetailsService repoDetailsServiceMock

    void setup() {
        repoDetailsServiceMock = Mock()
        repoController = new RepoController(repoDetailsServiceMock)
    }

    def "calls RepoService to get repo details"(){
        given:
        def expectedOwner = "expectedOwner"
        def expectedRepo = "expectedRepo"

        when:
        repoController.getRepoDetails(expectedOwner, expectedRepo)

        then:
        1 * repoDetailsServiceMock.requestRepoDetails(expectedOwner, expectedRepo) >> Optional.of(Stub(RepoDetailsModel))
    }

    def "returns ResponseEntity from repoService"(){
        given:
        RepoDetailsModel expectedRepoDetailsMock = Mock()

        def expectedServiceResponse = Optional.of(expectedRepoDetailsMock)
        repoDetailsServiceMock.requestRepoDetails(*_) >> expectedServiceResponse

        when:
        def actualResponseEntity = repoController.getRepoDetails("validUser", "validRepoName")

        then:
        actualResponseEntity.getStatusCode() == HttpStatus.OK
        actualResponseEntity.getBody() == expectedRepoDetailsMock
    }

    def "rethrows RepoNotFoundException"(){
        given:
        repoDetailsServiceMock.requestRepoDetails(*_) >> {throw new RepoNotFoundException("msg") }

        when:
        repoController.getRepoDetails("validUser", "validRepoName")

        then:
        thrown RepoNotFoundException
    }

    def "rethrows IllegalArgException from service"(){
        given:
        repoDetailsServiceMock.requestRepoDetails(*_) >> {throw new IllegalArgumentException("msg") }

        when:
        repoController.getRepoDetails("validUser", "validRepoName")

        then:
        thrown IllegalArgumentException
    }

    def "throws GitHubUnavailableException if result is missing"(){
        given:
        repoDetailsServiceMock.requestRepoDetails(*_) >> Optional.empty()

        when:
        repoController.getRepoDetails("validUser", "validRepoName")

        then:
        thrown GitHubUnavailableException
    }

    def "throws IllegalArgumentException on invalid params"(){
        expect:
        try{
            repoController.getRepoDetails(user, repo)
            assert false
        }catch(IllegalArgumentException e){
            assert true
        }

        where:
        user            | repo
        null            | "validRepoName"
        "validUser"     | null
        null            | null
        ""              | "validRepoName"
        "validUser"     | ""
        ""              | ""
    }

    def "repoNotFoundExceptionHandler returns 404 response with error body on RepoNotFoundException"(){
        given:
        String expectedMessage = "Requested user / repo pair was not found"

        when:
        def result = repoController.repoNotFoundExceptionHandler(new RepoNotFoundException(expectedMessage))

        then:
        result.getStatusCode() == HttpStatus.NOT_FOUND
        result.getBody().getMessage() == expectedMessage
    }

    def "illegalArgumentExceptionHandler returns 400 response on invalid repo/user params"(){
        given:
        String expectedMessage = "Requested user / repo pair was not found"

        when:
        def result = repoController.illegalArgumentExceptionHandler(new IllegalArgumentException(expectedMessage))

        then:
        result.getStatusCode() == HttpStatus.BAD_REQUEST
        result.getBody().getMessage() == expectedMessage
    }

    def "gitHubUnavailableExceptionHandler returns 503 response when there is no access to GitHub"(){
        given:
        String expectedMessage = "GitHub unavailable"

        when:
        def result = repoController.gitHubUnavailableExceptionHandler(new GitHubUnavailableException(expectedMessage))

        then:
        result.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE
        result.getBody().getMessage() == expectedMessage
    }
}
