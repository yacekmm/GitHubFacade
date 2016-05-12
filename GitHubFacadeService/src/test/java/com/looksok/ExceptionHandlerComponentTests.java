package com.looksok;

import com.looksok.application.GitHubFacadeServiceApplication;
import com.looksok.controller.RepoController;
import com.looksok.service.repo.details.RepoDetailsService;
import com.looksok.service.repo.details.exception.RepoNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GitHubFacadeServiceApplication.class)
@WebAppConfiguration
public class ExceptionHandlerComponentTests {

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

        this.mockMvc
                .perform(get("/repositories/user/repo") )
                .andExpect(status().isNotFound());
    }

    @Test
    public void returnsBadRequestStatusOnIllegalArgumentException() throws Exception {
        when(repoDetailsServiceMock.requestRepoDetails(anyString(), anyString())).thenThrow(IllegalArgumentException.class);

        this.mockMvc
                .perform(get("/repositories/user/repo") )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void returnsServiceUnavailableOnGitHubUnavailableException() throws Exception {
        when(repoDetailsServiceMock.requestRepoDetails(anyString(), anyString())).thenReturn(Optional.empty());

        this.mockMvc
                .perform(get("/repositories/user/repo") )
                .andExpect(status().isServiceUnavailable());
    }
}