package com.example.authsystem.cucumber.config;

import com.codeborne.selenide.Configuration;
import com.example.authsystem.AuthSystemApplication;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@CucumberContextConfiguration
@SpringBootTest(
        classes = AuthSystemApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class SpringCucumberConfiguration {

    @LocalServerPort
    private int port;

    @Before(order = 0)
    public void configureBaseUrl() {
        Configuration.baseUrl = "http://localhost:" + port;
    }
}
