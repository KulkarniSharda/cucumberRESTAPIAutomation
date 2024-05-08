package com.agileautomation.common;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MockServerConfig {
    RequestSpecification specification;
    public static WireMockServer mockServer;

    public static void startServer() {
//create an instance of mock server and define the port num: 8081
        mockServer = new WireMockServer(WireMockConfiguration.options().port(8081));
//configure host and port
        WireMock.configureFor("localhost", 8081);
        mockServer.start();
    }

    public static void stubForGet() {
        String respBody = "{\"id\": 6,\n" +
                "            \"email\": \"tracey.ramos@reqres.in\",\n" +
                "            \"first_name\": \"Tracey\",\n" +
                "            \"last_name\": \"Ramos\",\n" +
                "            \"avatar\": \"https://reqres.in/img/faces/6-image.jpg\"}";

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/contact"))
                .willReturn(WireMock.aResponse()
                .withHeader("Content-Type", ContentType.JSON.toString())
                .withBody(respBody)));
    }

    public static void stubForPost() {
        String respBody = "{\"id\": 6,\n" +
                "            \"email\": \"tracey.ramos@reqres.in\",\n" +
                "            \"first_name\": \"Tracey\",\n" +
                "            \"last_name\": \"Ramos\",\n" +
                "            \"avatar\": \"https://reqres.in/img/faces/6-image.jpg\"}";

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/contact"))
                .willReturn(WireMock.aResponse().withHeader("Content-Type", ContentType.JSON.toString())
                        .withBody(respBody)));
    }

    public static void stopServer() {
        mockServer.stop();
    }

    public static void main(String[] agr) {
        startServer();
        stubForGet();
//hit an api to get the response from mock server
        RequestSpecification specification = RestAssured.given();
        Response response = specification.baseUri("http://localhost:8081")
                .log().all()
                .get("/contact");
        response.prettyPrint();
        stopServer();
    }
}