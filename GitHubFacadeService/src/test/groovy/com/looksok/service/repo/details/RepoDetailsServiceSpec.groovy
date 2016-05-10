package com.looksok.service.repo.details

import com.looksok.constants.ConstAppLogic
import com.looksok.service.rest.RestTemplatePrototype
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
        def expectedUri = new URI(ConstAppLogic.GitHubUrl.REPOS + expectedUser + "/" + expectedRepo)

        when:
        repoDetailsService.requestRepoDetails(expectedUser, expectedRepo)

        then:
        1 * restTemplatePrototypeMock.getRestTemplate() >> restTemplateMock
        1 * restTemplateMock.getForObject(_, _) >> {actualUrl, type ->
            assert actualUrl == expectedUri
        }
    }
}
