package com.agileautomation.common;

import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Hooks {

    @BeforeAll
    public static void init() {
        MockServerConfig.startServer();
        MockServerConfig.stubForGet();
        MockServerConfig.stubForPost();
    }

    @AfterAll
    public static void tearDown() {
//stop mock server
        MockServerConfig.stopServer();
    }

    @Before("@GetMockData")
    public void getMockData() {
        RequestSpecification specification = RestAssured.given();
        Response response = specification.baseUri("http://localhost:8081")
                .basePath( "/contact").
        header("Accept", ContentType.JSON)
                .log().all().get();
        response.prettyPrint();
    }
}
