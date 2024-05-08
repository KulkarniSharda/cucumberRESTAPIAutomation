package com.agileautomation.stepdefination;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DealsStepdefination {
    //dev/api/opportunity
    RequestSpecification specification;
    Response response;
    String dealname;
    String requestBody;
    String milestoneName;

    @Given("I setup an request structure")
    public void setup() {
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com")
                .basePath("dev/api/")
                .auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Accept", "application/json")
                .log().all();

    }

    @When("I hit an api for deals")
    public void iHitAnApiForDeals() {

        response = specification.get("opportunity");
    }

    @Then("I receive response of deals")
    public void iReceiveResponseOfDeals() {
        response.prettyPrint();


        int statuscode = response.getStatusCode();
        Assert.assertEquals(200, statuscode);

        List<Object> responselist = response.jsonPath().getList("");
//        System.out.println(responselist);
        for (Object obj : responselist) {
//            System.out.println(obj);
            Map<String, Object> objlist = (Map<String, Object>) obj;
            String nameofdeal = objlist.get("name").toString();
            String milstone = objlist.get("milestone").toString();
            System.out.println(milstone);
            System.out.println(nameofdeal);

            Object str = objlist.get("owner");
//            System.out.println(str);

            Map<String, Object> obj1 = (Map<String, Object>) str;
            String name = obj1.get("name").toString();
//            System.out.println(name);

            Object str1 = objlist.get("custom_data");
            List<Object> customdata = (List<Object>) str1;
            for (Object str1list : customdata
            ) {
                System.out.println(str1list);

                Map<String, Object> map = (Map<String, Object>) str1list;
                String name1 = map.get("name").toString();
                System.out.println(name1);
            }


        }

    }

    @Given("I setup request structure to create Deal with valid details")
    public void iSetupRequestStructureToCreateDealWithValidDetails(Map<String, String> dealData) {

        dealname = dealData.get("dealname");
        milestoneName = dealData.get("milestoneName");

        requestBody = "{\n" +
                "    \"name\": \"Deal-Zombez\",\n" +
                "    \"expected_value\": \"1000\",\n" +
                "    \"probability\": \"75\",\n" +
                "    \"close_date\": 1455042600,\n" +
                "    \"milestone\": \"Proposal\",\n" +
                "    \"contact_ids\": [\n" +
                "        \"5602613134163968\"\n" +
                "    ],\n" +
                "    \"custom_data\": [\n" +
                "        {\n" +
                "            \"name\": \"Group Size\",\n" +
                "            \"value\": \"20\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        specification = RestAssured.given();specification.baseUri("https://automationagile.agilecrm.com")
                .basePath("/dev/api")
                .auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Accept", ContentType.JSON).header("Content-Type", "application/json")
                .body(requestBody)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all();
    }

    @When("I hit a create Deal api")
    public void iHitACreateDealApi() {
        response = specification.post("opportunity");
    }

    @Then("I verify Deal created successfully")
    public void iVerifyDealCreatedSuccessfully() {
        response.prettyPrint();
        //5063481660080128 id
        Assert.assertEquals(200, response.getStatusCode());
        String actualmile = response.jsonPath().get("milestone");
        Assert.assertEquals(actualmile, milestoneName);
        System.out.println("milestoneval=" + actualmile);
        System.out.println("i am here");
        List<Map<String, String>> propList = response.jsonPath().getList("milestone");
        for (Map<String, String> propObject : propList) {

            if (propObject.get("name").equals("name")) {
                String actualdealName = propObject.get("value");
                Assert.assertEquals(dealname, actualdealName);
            } else if (propObject.get("name").equals("name")) {
                String actualmilestoneName = propObject.get("value");
                Assert.assertEquals(milestoneName, actualmilestoneName);
            } else {
                System.out.println("I am out of condition");
            }

        }
    }

    @Given("I setup request structure to create Deal using json file")
    public void iSetupRequestStructureToCreateDealUsingJsonFile() throws IOException, ParseException {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/RequestFiles/CreateDealRequest.json";
        File file = new File(filePath);
//read the content of the file
        FileReader fileReader = new FileReader(file);
//create an instance of Parser
        JSONParser jsonParser = new JSONParser();
        Object inputObject = jsonParser.parse(fileReader); //read content line/line
        JSONObject jsonObject = (JSONObject) inputObject;
        milestoneName = jsonObject.get("milestone").toString();
        dealname = jsonObject.put("name","zosefDeal").toString();
       // dealname = jsonObject.get("name").toString();
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("/dev/api")
                .auth()
                .basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Accept", ContentType.JSON).header("Content-Type", "application/json")
                .body(file)
                .body(jsonObject)
                .accept(ContentType.JSON).contentType(ContentType.JSON).log().all();

    }
    @Then("I verify Deal created successfully with {int} status code")
    public void iVerifyDealCreatedSuccessfullyWithStatusCode(int StatusCode) {
        response.prettyPrint();
        Assert.assertEquals(StatusCode, response.getStatusCode());
        String actualmile = response.jsonPath().get("milestone");
        Assert.assertEquals(actualmile, milestoneName);
        System.out.println("actualmile= " + actualmile);
        System.out.println("Expect = " + milestoneName);

        String actualDealname = response.jsonPath().get("name");
        Assert.assertEquals(actualDealname, dealname);
        System.out.println("expe + actualname =" + dealname + actualDealname);
    }
}



