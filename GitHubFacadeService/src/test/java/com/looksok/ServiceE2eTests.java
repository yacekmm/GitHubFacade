package com.looksok;

import com.looksok.application.GitHubFacadeServiceApplication;
import com.looksok.controller.RepoController;
import com.looksok.service.repo.details.RepoDetailsService;
import com.looksok.service.repo.details.exception.GitHubUnavailableException;
import com.looksok.service.repo.details.model.GitHubRepoModelSimple;
import com.looksok.service.rest.RestTemplateSingleton;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Mock
    private RestTemplate restTemplateMock;

    @Autowired
    RepoController repoController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(restTemplateSingletonMock.getInstance()).thenReturn(restTemplateMock);
        mockMvc = MockMvcBuilders.standaloneSetup(repoController).build();
    }

    @Test
    public void returnsOkWithResult() throws Exception {
        GitHubRepoModelSimple gitHubModelStub = mock(GitHubRepoModelSimple.class);
        when(gitHubModelStub.getCreated_at()).thenReturn("2011-11-11T12:12:12Z");
        when(gitHubModelStub.getDescription()).thenReturn("someDescription");
        when(gitHubModelStub.getStargazers_count()).thenReturn(123);
        when(gitHubModelStub.getFull_name()).thenReturn("LongName");

        when(restTemplateMock.exchange(anyObject(), anyObject(), anyObject(), eq(GitHubRepoModelSimple.class)))
            .thenReturn(new ResponseEntity<>(gitHubModelStub, HttpStatus.OK));

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

    @Test
    public void returnsServiceUnavailableOnGitHubUnavailableException() throws Exception {
        when(restTemplateMock.exchange(anyObject(), anyObject(), anyObject(), eq(GitHubRepoModelSimple.class)))
                .thenThrow(GitHubUnavailableException.class);

        mockMvc
                .perform(get("/repositories/user/repo").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void returnsServiceUnavailableOnRestClientException() throws Exception {
        when(restTemplateMock.exchange(anyObject(), anyObject(), anyObject(), eq(GitHubRepoModelSimple.class)))
                .thenThrow(RestClientException.class);

        mockMvc
                .perform(get("/repositories/user/repo").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isServiceUnavailable());
    }
}