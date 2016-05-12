package com.looksok.service.repo.details.model

import spock.lang.Specification

class RepoDetailsSpec extends Specification {

    def "FromGitHubModel returns constructed RepoDetails"() {
        given:
        String expectedCloneUri = "expectedCloneUri"
        String expectedCreatedAt = "2016-04-05T16:39:50Z"
        String expectedDescription = "someDescription"
        String expectedFullName = "fullName"
        Number expectedStarsCount = 13

        GitHubRepoModelSimple gitHubModel = Mock()
        gitHubModel.getCreated_at() >> expectedCreatedAt
        gitHubModel.getDescription() >> expectedDescription
        gitHubModel.getFull_name() >> expectedFullName
        gitHubModel.getStargazers_count() >> expectedStarsCount
        gitHubModel.getClone_url() >> expectedCloneUri

        when:
        def result = RepoDetailsModel.fromGitHubModel(gitHubModel)

        then:
        result.getCreatedAt() == "2016-04-05 18:39:50"
        result.getDescription() == expectedDescription
        result.getFullName() == expectedFullName
        result.getStars() == expectedStarsCount
        result.getCloneUrl() == expectedCloneUri
    }
}