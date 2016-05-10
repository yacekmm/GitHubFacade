package com.looksok.controller

import spock.lang.Specification

class RepoControllerSpec extends Specification {

    private def repoController

    void setup() {
        repoController = new RepoController()
    }

    def "testsConfig"(){
        given:


        when:
        def result = repoController.giveOne()

        then:
        result == 1
    }
}
