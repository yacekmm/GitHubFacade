package com.looksok.service.repo.details

import com.looksok.constants.ConstAppLogic
import com.looksok.service.rest.RestTemplatePrototype
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        1 * restTemplateMock.getForEntity(_, _) >> {actualUrl, type ->
            assert actualUrl == expectedUri
            new ResponseEntity<RepoDetailsDto>(HttpStatus.OK)
        }
    }

    def "RequestRepoDetails returns result as a parsed object"() {
        given:
        def responseEntityMock = Mock(ResponseEntity)
        restTemplateMock.getForEntity(_, RepoDetailsDto.class) >> responseEntityMock

        when:
        def repoDetails = repoDetailsService.requestRepoDetails("anyUser", "anyRepo")

        then:
        repoDetails.isPresent()
        repoDetails.get() == responseEntityMock
    }


    def "RequestRepoDetails returns 503 response on unknown host exception"() {
        given:
        restTemplateMock.getForEntity(_, _) >> { throw new UnknownHostException() }

        when:
        def repoDetails = repoDetailsService.requestRepoDetails("anyUser", "anyRepo")

        then:
        repoDetails.isPresent()
        repoDetails.get() == new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE)
    }

    //API response
    //{"id":55524725,"name":"IDEConfig","full_name":"yacekmm/IDEConfig","owner":{"login":"yacekmm","id":7893442,"avatar_url":"https://avatars.githubusercontent.com/u/7893442?v=3","gravatar_id":"","url":"https://api.github.com/users/yacekmm","html_url":"https://github.com/yacekmm","followers_url":"https://api.github.com/users/yacekmm/followers","following_url":"https://api.github.com/users/yacekmm/following{/other_user}","gists_url":"https://api.github.com/users/yacekmm/gists{/gist_id}","starred_url":"https://api.github.com/users/yacekmm/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/yacekmm/subscriptions","organizations_url":"https://api.github.com/users/yacekmm/orgs","repos_url":"https://api.github.com/users/yacekmm/repos","events_url":"https://api.github.com/users/yacekmm/events{/privacy}","received_events_url":"https://api.github.com/users/yacekmm/received_events","type":"User","site_admin":false},"private":false,"html_url":"https://github.com/yacekmm/IDEConfig","description":"My IDE settings","fork":false,"url":"https://api.github.com/repos/yacekmm/IDEConfig","forks_url":"https://api.github.com/repos/yacekmm/IDEConfig/forks","keys_url":"https://api.github.com/repos/yacekmm/IDEConfig/keys{/key_id}","collaborators_url":"https://api.github.com/repos/yacekmm/IDEConfig/collaborators{/collaborator}","teams_url":"https://api.github.com/repos/yacekmm/IDEConfig/teams","hooks_url":"https://api.github.com/repos/yacekmm/IDEConfig/hooks","issue_events_url":"https://api.github.com/repos/yacekmm/IDEConfig/issues/events{/number}","events_url":"https://api.github.com/repos/yacekmm/IDEConfig/events","assignees_url":"https://api.github.com/repos/yacekmm/IDEConfig/assignees{/user}","branches_url":"https://api.github.com/repos/yacekmm/IDEConfig/branches{/branch}","tags_url":"https://api.github.com/repos/yacekmm/IDEConfig/tags","blobs_url":"https://api.github.com/repos/yacekmm/IDEConfig/git/blobs{/sha}","git_tags_url":"https://api.github.com/repos/yacekmm/IDEConfig/git/tags{/sha}","git_refs_url":"https://api.github.com/repos/yacekmm/IDEConfig/git/refs{/sha}","trees_url":"https://api.github.com/repos/yacekmm/IDEConfig/git/trees{/sha}","statuses_url":"https://api.github.com/repos/yacekmm/IDEConfig/statuses/{sha}","languages_url":"https://api.github.com/repos/yacekmm/IDEConfig/languages","stargazers_url":"https://api.github.com/repos/yacekmm/IDEConfig/stargazers","contributors_url":"https://api.github.com/repos/yacekmm/IDEConfig/contributors","subscribers_url":"https://api.github.com/repos/yacekmm/IDEConfig/subscribers","subscription_url":"https://api.github.com/repos/yacekmm/IDEConfig/subscription","commits_url":"https://api.github.com/repos/yacekmm/IDEConfig/commits{/sha}","git_commits_url":"https://api.github.com/repos/yacekmm/IDEConfig/git/commits{/sha}","comments_url":"https://api.github.com/repos/yacekmm/IDEConfig/comments{/number}","issue_comment_url":"https://api.github.com/repos/yacekmm/IDEConfig/issues/comments{/number}","contents_url":"https://api.github.com/repos/yacekmm/IDEConfig/contents/{+path}","compare_url":"https://api.github.com/repos/yacekmm/IDEConfig/compare/{base}...{head}","merges_url":"https://api.github.com/repos/yacekmm/IDEConfig/merges","archive_url":"https://api.github.com/repos/yacekmm/IDEConfig/{archive_format}{/ref}","downloads_url":"https://api.github.com/repos/yacekmm/IDEConfig/downloads","issues_url":"https://api.github.com/repos/yacekmm/IDEConfig/issues{/number}","pulls_url":"https://api.github.com/repos/yacekmm/IDEConfig/pulls{/number}","milestones_url":"https://api.github.com/repos/yacekmm/IDEConfig/milestones{/number}","notifications_url":"https://api.github.com/repos/yacekmm/IDEConfig/notifications{?since,all,participating}","labels_url":"https://api.github.com/repos/yacekmm/IDEConfig/labels{/name}","releases_url":"https://api.github.com/repos/yacekmm/IDEConfig/releases{/id}","deployments_url":"https://api.github.com/repos/yacekmm/IDEConfig/deployments","created_at":"2016-04-05T16:39:50Z","updated_at":"2016-04-05T16:39:50Z","pushed_at":"2016-04-06T13:31:36Z","git_url":"git://github.com/yacekmm/IDEConfig.git","ssh_url":"git@github.com:yacekmm/IDEConfig.git","clone_url":"https://github.com/yacekmm/IDEConfig.git","svn_url":"https://github.com/yacekmm/IDEConfig","homepage":null,"size":47,"stargazers_count":0,"watchers_count":0,"language":null,"has_issues":true,"has_downloads":true,"has_wiki":true,"has_pages":false,"forks_count":0,"mirror_url":null,"open_issues_count":0,"forks":0,"open_issues":0,"watchers":0,"default_branch":"master","network_count":0,"subscribers_count":1}
}
