package com.looksok.application;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GitHubFacadeServiceApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class GitHubFacadeServiceIntegrationTest {

	@Value("${local.server.port}")
	int port;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;
    }

    @Test
    public void okResponseOnSampleRepo() {
        RestAssured
            .when()
                .get("/repositories/yacekmm/IDEConfig")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("fullName", equalTo("yacekmm/IDEConfig"))
                .body("cloneUrl", equalTo("https://github.com/yacekmm/IDEConfig.git"));
    }

    @Test
    public void notFoundResponseOnNotExistingRepo() {
        RestAssured
            .when()
                .get("/repositories/yacekmm/iWillNeverCreateSuchRepo")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
