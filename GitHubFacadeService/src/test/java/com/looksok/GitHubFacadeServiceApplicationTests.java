package com.looksok;

import com.looksok.application.GitHubFacadeServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GitHubFacadeServiceApplication.class)
@WebAppConfiguration
public class GitHubFacadeServiceApplicationTests {

	@Test
	public void contextLoads() {
	}

	//TODO JM: check RepoService Component loaded
}
