package com.looksok.controller

import com.looksok.service.RepoDetailsService
import spock.lang.Specification

class RepoControllerSpec extends Specification {

    private RepoController repoController
    private def repoDetailsService

    void setup() {
        repoDetailsService = Mock(RepoDetailsService)
        repoController = new RepoController(repoDetailsService)
    }

    def "calls RepoService to get repo details"(){
        given:


        when:
        repoController.getRepoDetails()

        then:
        1 * repoDetailsService.requestRepoDetails()
    }
}
