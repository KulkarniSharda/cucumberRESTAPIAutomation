package com.agileautomation.stepdefination;

import com.agileautomation.pojo.CreateUserPojo;
import com.agileautomation.pojo.CreateUserResponsePojo;
import com.agileautomation.pojo.UsersResponsePojo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ReqResStepdefination {
    RequestSpecification specification;
    Response response;
    Response resp;
    CreateUserPojo userPojo;
    CreateUserResponsePojo responsePojo;

    @Given("I setup a request structure to get all users information")
    public void setup() {
        specification = RestAssured.given();
        specification.baseUri("https://reqres.in/")
                .basePath("/api/users")
                .log().all();
    }

    @When("I hit an get all users api")
    public void iHitAnGetAllUsersApi() {
        response = specification.get();
    }

    @Then("I verify all users response")
    public void iVerifyAllUsersResponse() {
        response.prettyPrint();
        response.getStatusCode();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);

        List<Long> ids = response.jsonPath().getList("data.id");
        System.out.println(ids);

        List<String> emailId = response.jsonPath().getList("data.email");
        System.out.println(emailId);

        Map<String, Object> userObject = response.jsonPath().getMap("data[0]");
        System.out.println(userObject);

        Long pagNum = response.jsonPath().getLong("page");
        System.out.println(pagNum);
    }

    @Then("I verify total users in response")
    public void iVerifyTotalUsersInResponse() {

        Long totalPages = response.jsonPath().getLong("total_pages");
        System.out.println(totalPages);//get total no of pages available with user info

        Long totalUsers = response.jsonPath().getLong("total");
        System.out.println(totalUsers);//get total no of resords available in response

        List<Object> dataList = response.jsonPath().getList("data");
        int firstPagedataSize = dataList.size();

        Long size = Long.parseLong(String.valueOf(firstPagedataSize));

        Long perPageSize = response.jsonPath().getLong("per_page");

        Assert.assertEquals(size, perPageSize);
        long nextPageRecord = totalUsers - firstPagedataSize;//10
        long leftRecords;
        for (int i = 2; i <= perPageSize; i++) {
            RequestSpecification reqSpec = RestAssured.given();

            resp = reqSpec.baseUri("https://reqres.in")
                    .queryParam("page", i)
                    .basePath("/api/users")
                    .log().all().get();
            resp.prettyPrint();
            long datasize = resp.jsonPath().getList("data").size();//4
            leftRecords = nextPageRecord - datasize;
            if (leftRecords != 0 && datasize == 0) {
                Assert.assertTrue(datasize <= leftRecords);
            }
        }
    }

    @Given("I setup request structure to create users")
    public void iSetupRequestStructureToCreateUsersSerialization() {
//Create an instance of encapsulation class to assign values against variables uses getter and setter method
        CreateUserPojo userPojo = new CreateUserPojo();
        userPojo.setName("Cyber Success");
        userPojo.setJob("QA Engineer");

        specification = RestAssured.given();
        specification.baseUri("https://regres.in").basePath("/api/users")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(userPojo).log().all();

    }

    @When("I hit a api to create user")
    public void initAApiToCreateUser() {
        response = specification.post();

    }

    @Then("I verify user created successfully")
    public void iVerifyUserCreatedSuccessfully() {
        response.prettyPrint();

    }

    @Then("I verify all users response using deserialization")
    public void iVerifyAllUsersResponseUsingDeserialization() {
        Assert.assertEquals(200, response.getStatusCode());
//deserialization of response into an object
        UsersResponsePojo usersResponsePojo = response.as(UsersResponsePojo.class);
        System.out.println(usersResponsePojo.getPage());
        System.out.println(usersResponsePojo.getData());
        for (Map<String, Object> dataMap : usersResponsePojo.getData()) {
            System.out.println(dataMap.get("id"));
            System.out.println(usersResponsePojo.getSupport().get("url"));


        }
    }
    @Given("I setup request structure to create users using serialization")
    public void iSetupRequestStructureToCreateUsersUsingSerialization() {
        CreateUserPojo userPojo = new CreateUserPojo();
        userPojo.setName("Cybers ");
        userPojo.setJob("QA Tester");

        specification = RestAssured.given();
        specification.baseUri("https://regres.in").basePath("/api/users")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(userPojo).log().all();
    }
    @Then("I verify user created successfully using deserialization")
    public void iVerifyUserCreatedSuccessfullyUsingDeserialization() {
        response.prettyPrint();
        Assert.assertEquals(201, response.getStatusCode());
        CreateUserResponsePojo responsePojo = response.as(CreateUserResponsePojo.class);
        String expectedName = userPojo.getName();
        String actualName = responsePojo.getName();
        Assert.assertEquals(expectedName, actualName);
        String expectedJob = userPojo.getJob();
        String actualJob = responsePojo.getJob();
        Assert.assertEquals(expectedJob, actualJob);
    }


}