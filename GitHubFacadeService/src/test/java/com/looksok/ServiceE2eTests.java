package com.looksok;

import com.looksok.application.GitHubFacadeServiceApplication;
import com.looksok.controller.RepoController;
import com.looksok.service.repo.details.RepoDetailsService;
import com.looksok.service.repo.details.exception.GitHubUnavailableException;
import com.looksok.service.rest.RestTemplateSingleton;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GitHubFacadeServiceApplication.class)
@WebAppConfiguration
public class ServiceE2eTests {


    private MockMvc mockMvc;

    @Mock
    RestTemplateSingleton restTemplateSingletonMock;

    @Autowired
    @InjectMocks
    RepoDetailsService repoDetailsServiceMock;

    @Autowired
    RepoController repoController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(repoController).build();
    }

    @Test
    public void returnsServiceUnavailableOnGitHubUnavailableException() throws Exception {
        when(restTemplateSingletonMock.getInstance()).thenThrow(GitHubUnavailableException.class);

        mockMvc
                .perform(get("/repositories/user/repo").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void returnsServiceUnavailableOnRestClientException() throws Exception {
        when(restTemplateSingletonMock.getInstance()).thenThrow(RestClientException.class);

        mockMvc
                .perform(get("/repositories/user/repo").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isServiceUnavailable());
    }
}