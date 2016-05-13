package com.looksok;

import com.looksok.application.GitHubFacadeServiceApplication;
import com.looksok.controller.RepoController;
import com.looksok.service.repo.details.RepoDetailsService;
import com.looksok.service.repo.details.exception.RepoNotFoundException;
import com.looksok.service.repo.details.model.GitHubRepoModelSimple;
import com.looksok.service.repo.details.model.RepoDetailsModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GitHubFacadeServiceApplication.class)
@WebAppConfiguration
public class RepoControllerComponentTests {

    private RepoController repoController;

    private MockMvc mockMvc;
    private RepoDetailsService repoDetailsServiceMock;

    @Before
    public void setup() {
        repoDetailsServiceMock = mock(RepoDetailsService.class);
        repoController = new RepoController(repoDetailsServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(repoController).build();
    }

    @Test
    public void returnsNotFoundStatusOnRepoNotFoundException() throws Exception {
        when(repoDetailsServiceMock.requestRepoDetails(anyString(), anyString())).thenThrow(RepoNotFoundException.class);

        mockMvc
                .perform(get("/repositories/user/repo").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void returnsBadRequestStatusOnIllegalArgumentException() throws Exception {
        when(repoDetailsServiceMock.requestRepoDetails(anyString(), anyString())).thenThrow(IllegalArgumentException.class);

        mockMvc
                .perform(get("/repositories/user/repo").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void returnsServiceUnavailableOnEmptyOptional() throws Exception {
        when(repoDetailsServiceMock.requestRepoDetails(anyString(), anyString())).thenReturn(Optional.empty());

        mockMvc
                .perform(get("/repositories/user/repo").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void returnsOkWithResult() throws Exception {
        GitHubRepoModelSimple gitHubModelMock = mock(GitHubRepoModelSimple.class);
        when(gitHubModelMock.getCreated_at()).thenReturn("2011-11-11T12:12:12Z");
        when(gitHubModelMock.getDescription()).thenReturn("someDescription");
        when(gitHubModelMock.getStargazers_count()).thenReturn(123);
        when(gitHubModelMock.getFull_name()).thenReturn("LongName");
        RepoDetailsModel repoDetailsModel = RepoDetailsModel.fromGitHubModel(gitHubModelMock);

        when(repoDetailsServiceMock.requestRepoDetails(anyString(), anyString())).thenReturn(Optional.of(repoDetailsModel));

        mockMvc
            .perform(get("/repositories/user/repo").accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("createdAt").value("2011-11-11 13:12:12"))
            .andExpect(jsonPath("description").value("someDescription"))
            .andExpect(jsonPath("stars").value(123))
            .andExpect(jsonPath("fullName").value("LongName"))
            .andDo(print());
    }
}