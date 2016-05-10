package com.looksok.controller

import com.looksok.service.repo.details.RepoDetailsService
import com.looksok.service.repo.details.RepoDetailsDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        1 * repoDetailsServiceMock.requestRepoDetails(expectedOwner, expectedRepo)
    }

    def "returns ResponseEntity from repoService"(){
        given:
        RepoDetailsDto expectedRepoDetailsMock = Mock()
        def expectedResponseEntity = new ResponseEntity<RepoDetailsDto>(expectedRepoDetailsMock, HttpStatus.OK)
        repoDetailsServiceMock.requestRepoDetails(*_) >> expectedResponseEntity

        when:
        def actualResponseEntity = repoController.getRepoDetails("anyUser", "anyRepo")

        then:
        actualResponseEntity.getStatusCode() == HttpStatus.OK
        actualResponseEntity.getBody() == expectedRepoDetailsMock
    }
}
