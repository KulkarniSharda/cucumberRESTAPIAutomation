package com.agileautomation.stepdefination;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TaskStepdefination {

    RequestSpecification specification;
    Response response;
    String priority_type;
    String requestBody;
    String taskstatus;
    String taskDescription;
    String  type;
    String subject;
    @Given("I setup an request structure for Task")
    public void setup() {
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("dev/api/").auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii").header("Accept", "application/json").log().all();

    }

    @When("I hit an api for Task")
    public void iHitAnApiForTask() {
        response = specification.get("tasks");
        response.prettyPrint();
    }

    @Then("I receive response of Task")
    public void iReceiveResponseOfTask() {
        response.prettyPrint();
        int statuscode = response.getStatusCode();
        Assert.assertEquals(200, statuscode);
    }


    @Given("I setup request structure to create Task with valid details")
    public void iSetupRequestStructureToCreateTaskWithValidDetails(DataTable table) {
        List<Map<String, String>> testData = table.asMaps();
        String name = testData.get(0).toString();
        System.out.println(name);

        requestBody = "{ \"progress\": \"0\",\n" +
                "    \"is_complete\": \"false\",\n" +
                "    \"subject\": \"Need to contact vendor\",\n" +
                "    \"type\": \"MEETING\",\n" +
                "    \"due\": 1459319400,\n" +
                "    \"task_ending_time\": \"12:00\",\n" +
                "    \"owner_id\": \"6263975862861824\",\n" +
                "    \"priority_type\": \"HIGH\",\n" +
                "    \"status\": \"YET_TO_START\",\n" +
                "    \"taskDescription\": \"This is very important. We need to discuss with few vendors about the product.\"}";
        specification = RestAssured.given();
        specification.baseUri("https://apiautomation.agilecrm.com").basePath("/dev/api")
                .auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Content", ContentType.JSON).header("Accept", ContentType.JSON)
                .body(requestBody).accept(ContentType.JSON).contentType(ContentType.JSON).log().all();


    }

    @When("I hit a create Task api")
    public void iHitACreateTaskApi() {
        response = specification.post("tasks");

    }


    @Then("I verify Task created successfully")
    public void iVerifyTaskCreatedSuccessfully() {
        response.prettyPrint();
        Assert.assertEquals(200, response.getStatusCode());
        //===
//        Assert.assertEquals(200, response.getStatusCode());
//        String actualmile = response.jsonPath().get("milestone");
//        Assert.assertEquals(actualmile, milestoneName);
//        System.out.println("milestoneval=" + actualmile);
//        System.out.println("i am here");
//        List<Map<String, String>> propList = response.jsonPath().getList("milestone");
//        for (Map<String, String> propObject : propList) {
//
//            if (propObject.get("name").equals("name")) {
//                String actualdealName = propObject.get("value");
//                Assert.assertEquals(dealname, actualdealName);
//            } else if (propObject.get("name").equals("name")) {
//                String actualmilestoneName = propObject.get("value");
//                Assert.assertEquals(milestoneName, actualmilestoneName);
//            } else {
//                System.out.println("I am out of condition");
//            }
//
//        }

        //===
    }

    @Given("I setup request structure to create Task using json file")
    public void iSetupRequestStructureToCreateTaskUsingJsonFile() throws IOException, ParseException {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/RequestFiles/CreateTaskRequest.json";
        File file = new File(filePath);
//read the content of the file
        FileReader fileReader = new FileReader(file);
//create an instance of Parser
        JSONParser jsonParser = new JSONParser();
        Object inputObject = jsonParser.parse(fileReader); //read content line/line
        JSONObject jsonObject = (JSONObject) inputObject;
        priority_type = jsonObject.get("priority_type").toString();
        taskDescription = jsonObject.put("taskDescription","Important Scrum call for API").toString();
        taskstatus = jsonObject.get("status").toString();
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("/dev/api")
                .auth()
                .basic("automatsionagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Accept", ContentType.JSON).header("Content-Type", "application/json")
                .body(file)
                .body(jsonObject)
                .accept(ContentType.JSON).contentType(ContentType.JSON).log().all();

    }

    @Then("I verify Task created successfully with {int} status code")
    public void iVerifyTaskCreatedSuccessfullyWithStatusCode(int StatusCode) {
        response.prettyPrint();

        System.out.println(response.getStatusCode());
        List<Map<String, String>> propList = response.jsonPath().getList("");
        System.out.println(propList);

      //  Assert.assertEquals(StatusCode, response.getStatusCode());

    }
}
