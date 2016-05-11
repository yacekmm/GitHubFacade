package com.looksok.service.repo.details.model

import spock.lang.Specification

class RepoDetailsSpec extends Specification {

    def "FromGitHubModel returns constructed RepoDetails"() {
        given:
        String expectedCloneUri = "expectedCloneUri"
        String expectedCreatedAt = "creationDateString"
        String expectedDescription = "someDescription"
        String expectedFullName = "fullName"
        Number expectedStarsCount = 13

        GitHubRepoModelSimple gitHubModel = Mock()
        gitHubModel.getCreatedAt() >> expectedCreatedAt
        gitHubModel.getDescription() >> expectedDescription
        gitHubModel.getFullName() >> expectedFullName
        gitHubModel.getStars() >> expectedStarsCount
        gitHubModel.getCloneUri() >> expectedCloneUri

        when:
        def result = RepoDetails.fromGitHubModel(gitHubModel)

        then:
        result.getCreatedAt() == expectedCreatedAt
        result.getDescription() == expectedDescription
        result.getFullName() == expectedFullName
        result.getStars() == expectedStarsCount
        result.getCloneUri() == expectedCloneUri
    }
}
