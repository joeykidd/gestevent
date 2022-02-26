package com.kumojin.gestevent.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kumojin.gestevent.GesteventApplication;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = GesteventApplication.class)
@AutoConfigureMockMvc
public abstract class BaseIT {
    protected WireMockServer wireMockServer;

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

}
