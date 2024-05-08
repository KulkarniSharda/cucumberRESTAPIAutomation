package com.agileautomation.stepdefination;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class SkTestStepdefination {
    RequestSpecification specification;
    Response response;
    //In types array, how many  "kind" field has "OBJECT" value. Print the count.
    //https://gorest.co.in/graphql/schema.json

    @Given("I setup a request structure to get all information")
    public void iSetupaRequestStructuregetInformation() {
        specification = RestAssured.given();

        specification.baseUri("https://gorest.co.in/")
                .basePath("graphql/schema.json")
                .log().all();

    }

    @When("I hit an api to get all info")
    public void iHitAnApiToGetAllInfo() {
        response = specification.get();
    }

    @Then("I verify api response with valid data and statusCode {int}")
    public void iVerifyApiResponseWithValidData(int RStatusCode) {
        //  response.prettyPrint();

        int statusCode = response.getStatusCode();
        //  Assert.assertEquals(RStatusCode, statusCode);
        List<Map<String, Object>> kinddata = response.jsonPath().getList("data.schema.types");
        System.out.println(kinddata);

        for (Map<String, Object> kindda : kinddata) {
            System.out.println();
            {
                System.out.println(kindda);

            }

            int size = kinddata.size();
            System.out.println(size);
            Map<String, Object> userObjectData = response.jsonPath().getMap("data");
            System.out.println(userObjectData);


        }
    }}
