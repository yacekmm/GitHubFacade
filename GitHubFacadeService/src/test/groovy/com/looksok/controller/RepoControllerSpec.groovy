package com.looksok.controller

import com.looksok.service.repo.details.RepoDetailsService
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
        1 * repoDetailsServiceMock.requestRepoDetails(expectedOwner, expectedRepo) >> Optional.empty()
    }

    def "returns 500 on unknown response from repoService"(){
        given:
        def serviceResult = Optional.empty()
        repoDetailsServiceMock.requestRepoDetails(*_) >> serviceResult

        when:
        def actualResponseEntity = repoController.getRepoDetails("validUser", "validRepoName")

        then:
        actualResponseEntity.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE
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

    def "returns BadRequest on invalid params"(){
        when:
        def actualResponseEntity_nullUser = repoController.getRepoDetails(null, "validRepoName")
        def actualResponseEntity_nullRepo = repoController.getRepoDetails("validUser", null)
        def actualResponseEntity_nullBoth = repoController.getRepoDetails(null, null)

        def actualResponseEntity_emptyUser = repoController.getRepoDetails("", "validRepoName")
        def actualResponseEntity_emptyRepo = repoController.getRepoDetails("validUser", "")
        def actualResponseEntity_emptyBoth = repoController.getRepoDetails("", "")

        then:
        actualResponseEntity_nullUser.getStatusCode() == HttpStatus.BAD_REQUEST
        actualResponseEntity_nullRepo.getStatusCode() == HttpStatus.BAD_REQUEST
        actualResponseEntity_nullBoth.getStatusCode() == HttpStatus.BAD_REQUEST

        actualResponseEntity_emptyUser.getStatusCode() == HttpStatus.BAD_REQUEST
        actualResponseEntity_emptyRepo.getStatusCode() == HttpStatus.BAD_REQUEST
        actualResponseEntity_emptyBoth.getStatusCode() == HttpStatus.BAD_REQUEST
    }

    def "repoNotFoundExceptionHandler returns 404 response with error body on RepoNotFoundException"(){
        given:
        String expectedMessage = "Requested user / repo pair was not found"

        when:
        def result = repoController.repoNotFoundExceptionHandler(new RepoNotFoundException(expectedMessage))

        then:
        result.getStatusCode() == HttpStatus.NOT_FOUND
    }
}
