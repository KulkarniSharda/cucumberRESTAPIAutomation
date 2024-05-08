package com.agileautomation.stepdefination;

import com.agileautomation.common.CommonFunctions;
import com.agileautomation.pojo.CreateCompanyPojo;
import io.cucumber.datatable.DataTable;
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
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//https://automationagile.agilecrm.com/dev/api/contacts and automationagile@yopmail.com and 9jissj53ko8khnppfi2r0432ii
public class CompanyStepdefination {
    RequestSpecification specification;
    Response response;
    String compName;
    String compUrl;
    String requestBody;

    CommonFunctions commonFunctions = new CommonFunctions();

    @Given("I setup a request structure to get all company information")
    public void setupRequestStructure(DataTable table) {
        specification = RestAssured.given().relaxedHTTPSValidation();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("/dev/api/contacts/companies/").auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                //.basic("apitestautomation@yopmail.com", "ibfe0nf749h5r50pv87aqm3e2c")
                .header("Accept", "application/json").log().all();
    }

    @When("I hit an api to get all company information")
    public void iHitAnApiToGetAllCompanyInformation() {

        response = specification.post("list");
    }

    @Then("I verify company response")
    public void iVerifyCompanyResponse() {
        //print the response in pretty format
        response.prettyPrint();
        //for id
        List<Long> idList = response.jsonPath().getList("id");
        System.out.println(idList);
        for (Long id : idList) {
            System.out.println(id);

            // for star value

            List<Integer> typelist = response.jsonPath().getList("star_value");
            System.out.println(typelist);
            for (Integer starv : typelist) {
                System.out.println(starv);
            }

            //verify status code
            int statusCode = response.getStatusCode();
            if (statusCode == 201) {
                System.out.println("Successfully verified the status code..");
            } else {
                System.out.println("Actual and Expected codes are not same..");
            }
//verify the type in the response
            //1. get all types from the response in the  form of list<String>
            List<String> typeList = response.jsonPath().getList("type");
            System.out.println(typeList);

            //iterate the list and verify each value is correct or not
            for (String type : typeList) {
                if (type.equals("COMPANY")) {
                    System.out.println("Successfully verified type of  company.");
                }
            }
//get the response in the form of list
            List<Object> listResponse = response.jsonPath().getList("");
            //get each objectso we have to iterate the list and convert it into Map object
            for (Object object : listResponse) {
                //typecast or convert object into Map<String,Object>
                Map<String, Object> comObj = (Map<String, Object>) object;

                //get the company type
                String companyType = comObj.get("type").toString();
                System.out.println(companyType);
                //get the name of each company
                //compObject.get("properties")

            }
        }
    }

    @When("I get name of all companies")
    public void iGetNameOfAllCompanie() {
        //get all companies object in a list

        List<Object> comList = response.jsonPath().getList("");
        //iterate through each company object
        for (Object compObj : comList) {

            //type cast each comp obj into Map<String,Object>
            Map<String, Object> comp = (Map<String, Object>) compObj;

            //get the value of properties array
            Object propObject = comp.get("properties");

            //type cast each the property object retrived from map object into List<object>
            List<Object> propList = (List<Object>) propObject;
            //iterate property list to get each object of property
            for (Object object : propList) {
                Map<String, String> mapObject = (Map<String, String>) object;
                //check if the value of name attribute is name or not
                if (mapObject.get("name").equals("name")) {
                    String id = comp.get("id").toString();
                    String name = mapObject.get("value");
                    System.out.println(id + ":" + name);
                }
            }
        }
    }

    @Given("I setup a request structure to get all company info with below keyword")
    public void iSetupARequestStructureToGetAllCompany(Map<String, String> params) {
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("/dev/api/contacts/companies/")
                //.queryParams(params)
                .auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii").header("Accept", "application/json").log().all();

    }

    @When("I hit an api to search company with keywords")
    public void iHitAnApiToSearchCompanyWithKeywsssords() {

        response = specification.post("list");
    }

    @Then("I verify the result according to keywords")
    public void iVerifyTheResultAccordingToKeywords() {
        response.prettyPrint();
        List<Object> companylist = response.jsonPath().getList("");
//        System.out.println(companylist);
        int companydatasize = companylist.size();
        System.out.println(companydatasize);
        for (Object firstlist : companylist) {
            Map<String, Object> proplist = (Map<String, Object>) firstlist;
            Object obj = proplist.get("properties");
            List<Object> listobj = (List<Object>) obj;
            for (Object obj1 : listobj) {
                Map<String, Object> mapobj = (Map<String, Object>) obj1;
                String nameval;
                if (mapobj.get("name").equals("name")) {
//                   String nameval=mapobj.get("value").toString();
                    nameval = mapobj.get("value").toString();
                    System.out.println(nameval);
                    List<String> typeList = response.jsonPath().getList("type");
                    System.out.println(typeList);
                    //iterate the list and verify each value is correct or not
                    for (String type : typeList) {
                        if (type.equals("COMPANY")) {
                            System.out.println("Successfully verified type of  company.");
                        }
                    }
//get the response in the form of list
                    companylist = response.jsonPath().getList("");
                    for (Object object : companylist) {
                        //typecast or convert object into Map<String,Object>
                        Map<String, Object> comObj = (Map<String, Object>) object;
                        //get the company type
                        String companyType = comObj.get("type").toString();
                        System.out.println(companyType);
                        //  Assert.assertEquals("Spicejet", nameval);
                    }
                    if (companylist.isEmpty()) {
                        long pathParam = (long) companylist.get(1);
                        specification.pathParam("spicejet", pathParam);

                    }
                }

            }

        }

        //  Assert.assertEquals();
    }

    @When("I get name of company which tags are not empty")
    public void nameCompanyTagsnotEmpty() {
        System.out.println("I get name of company which tags are not empty");
        List<Object> list = response.jsonPath().getList("");
        List<Map<String, Object>> lists = response.jsonPath().getList("");
        for (Object obj : lists) {
            Map<String, Object> compMap = (Map<String, Object>) obj;
            Object tag0bject = compMap.get("tags");
            List<String> taglist = (List<String>) tag0bject;
            if (!taglist.isEmpty()) {
                Object propObject = compMap.get("properties");
                List<Object> proplist = (List<Object>) propObject;
                for (Object propobj : proplist) {
                    Map<String, String> object = (Map<String, String>) propobj;
                    String name = object.get("name");
                    if (name.equals("name")) {
                        String compName = object.get("value");
                        System.out.println(compName);
                    }
                }
            }
        }
    }

    @Given("I setup request structure to get all company info with below keyword")
    public void iSetupRequestStructureToGetAllCompany(Map<String, String> params) {
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("/dev/api/search")
                .queryParams(params).auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Accept", "application/json").log().all();
    }

    @When("I hit an api to search company with keyword")
    public void iHitAnApiToSearchCompanyWithKeyword() {
        response = specification.post("");
    }

    @Then("I verify the result according to keyword")
    public void iVerifyTheResultAccordingToKeyword() {
        response.prettyPrint();
    }

    @Given("I setup request structure to create company with valid details")
    public void createCompany(Map<String, String> compData) {
        compName = compData.get("compName");
        compUrl = compData.get("url");

        requestBody = "{\n" + "    \"type\": \"COMPANY\",\n" + "    \"star_value\": 4,\n" + "    \"lead_score\": 120,\n" + "    \"tags\": [\n" + "        \"Permanent\",\n" + "        \"USA\",\n" + "        \"Hongkong\",\n" + "        \"Japan\"\n" + "    ],\n" + "    \"properties\": [\n" + "        {\n" + "            \"name\": \"Company Type\",\n" + "            \"type\": \"CUSTOM\",\n" + "            \"value\": \"MNC Inc\"\n" + "        },\n" + "        {\n" + "            \"type\": \"SYSTEM\",\n" + "            \"name\": \"name\",\n" + "            \"value\": \"" + compName + "\"\n" + "        },\n" + "        {\n" + "            \"type\": \"SYSTEM\",\n" + "            \"name\": \"url\",\n" + "            \"value\": \"" + compUrl + "\"\n" + "        },\n" + "        {\n" + "            \"name\": \"email\",\n" + "            \"value\": \"care@allein.com\",\n" + "            \"subtype\": \"\"\n" + "        },\n" + "        {\n" + "            \"name\": \"phone\",\n" + "            \"value\": \"45500000\",\n" + "            \"subtype\": \"\"\n" + "        },\n" + "        {\n" + "            \"name\": \"website\",\n" + "            \"value\": \"http://www.linkedin.com/company/agile-crm\",\n" + "            \"subtype\": \"LINKEDIN\"\n" + "        },\n" + "        {\n" + "            \"name\": \"address\",\n" + "            \"value\": \"{\\\"address\\\":\\\"MS 35, 440 N Wolfe Road\\\",\\\"city\\\":\\\"Sunnyvale\\\",\\\"state\\\":\\\"CA\\\",\\\"zip\\\":\\\"94085\\\",\\\"country\\\":\\\"US\\\"}\",\n" + "            \"subtype\": \"office\"\n" + "        }\n" + "    ]\n" + "}";
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("/dev/api").auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii").header("Accept", ContentType.JSON).header("Content-Type", "application/json").body(requestBody).accept(ContentType.JSON).contentType(ContentType.JSON).log().all();
    }

    @When("I hit a create company api")
    public void iHitACreateCompanyApi() {

        response = specification.post("/contacts");
    }

    @Then("I verify company created successfully")
    public void iVerifyCompanyCreatedSuccessfully() {
        response.prettyPrint();
        //verify status code
        Assert.assertEquals(200, response.getStatusCode());
        List<Map<String, String>> propList = response.jsonPath().getList("properties");
        for (Map<String, String> propObject : propList) {
            if (propObject.get("name").equals("name")) {
                String actualCompName = propObject.get("value");
                Assert.assertEquals(compName, actualCompName);
            } else if (propObject.get("name").equals("url")) {
                String actualUrl = propObject.get("value");
                Assert.assertEquals(compUrl, actualUrl);
            }
        }
    }

    //==================================================================================
    @Given("I setup request structure to create company with hashmap object")
    public void iSetupRequestStructureToCreateCompanyWithHashmapObject(DataTable table) {
        List<Map<String, String>> testData = table.asMaps();
        Map<String, String> data = testData.get(0);
        compName = data.get("compName");
        compUrl = data.get("compUrl");
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("type", "COMPANY");
        List<Map<String, String>> propList = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("name", "Comapny Type");
        map1.put("type", "CUSTOM");
        map1.put("value", "MNC Inc");

        propList.add(map1);
        Map<String, String> map2 = new HashMap<>();
        map2.put("type", "SYSTEM");
        map2.put("name", "name");
        Map<String, String> map3 = new HashMap<>();
        map3.put("type", "SYSTEM");
        map3.put("name", "url");
        if (compName == null) {
            map2.put("value", null);
            map3.put("value", compUrl);
        } else if (compUrl == null) {
            map2.put("value", compName);
            map3.put("value", null);
        } else if (compName == null && compUrl == null) {
            map2.put("value", null);
            map3.put("value", null);
        } else {
            map2.put("value", compName);
            map3.put("value", compUrl);
        }
        propList.add(map2);
        propList.add(map3);
        requestBody.put("properties", propList);
        {
            specification = RestAssured.given();
            specification.baseUri("https://apiautomation.agilecrm.com").basePath("/dev/api").auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")

                    .header("Content", ContentType.JSON).header("Accept", ContentType.JSON).body(requestBody).accept(ContentType.JSON).contentType(ContentType.JSON).log().all();

        }
    }

    @Then("I verify company created successfully with {int} status code")
    public void verifyCompanyCreatedSuccessfullyWithStatusCode(int StatusCode) {
        response.prettyPrint();
        Assert.assertEquals(StatusCode, response.getStatusCode());
        List<Map<String, String>> propList = response.jsonPath().getList("properties");
        for (Map<String, String> propboject : propList) {
            if (propboject.get("name").equals("name")) {
                String actualcompName = propboject.get("value");
                Assert.assertEquals(compName, actualcompName);
            } else if (propboject.get("name").equals("url")) {
                String actualUrl = propboject.get("value");
                Assert.assertEquals(compUrl, actualUrl);

            }
        }
    }

    @Given("I setup request structure to create company using json file")
    public void iSetupRequestStructureToCreateCompanyUsingJsonFile() throws IOException, ParseException {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/RequestFiles/CreateCompanyRequest.json";
        File file = new File(filePath);
//read the content of the file
        FileReader fileReader = new FileReader(file);
//create an instance of Parser
        JSONParser jsonParser = new JSONParser();
        Object inputObject = jsonParser.parse(fileReader); //read content line/line
        JSONObject jsonObject = (JSONObject) inputObject;
        JSONArray propArray = (JSONArray) jsonObject.get("properties");
//get the name abject present at index 1 in properties array list[]
        JSONObject nameObject = (JSONObject) propArray.get(1);
        // JSONObject.put("value", "Atlon") ;
        compName = nameObject.get("value").toString();
        JSONObject urlObject = (JSONObject) propArray.get(2);
        compUrl = urlObject.get("value").toString();
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("/dev/api")
                .auth()
                .basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Accept", ContentType.JSON).header("Content-Type", "application/json")
                .body(file)
                //.body(jsonObject)
                .accept(ContentType.JSON).contentType(ContentType.JSON).log().all();
    }

    @Given("I setup request structure to create company using pojo class")
    public void iSetupRequestStructureToCreateCompanyUsingPojoClass() {

//        Faker faker = new Faker();
//        String comfake = faker.company().toString();
//        System.out.println("CompName" + compName);

        CreateCompanyPojo userPojo = new CreateCompanyPojo();
        userPojo.setCompName("HarryMAX");
        userPojo.setCompUrl("https://harryMAx.com");
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("/dev/api/")
                .auth()
                .basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(userPojo)
                .accept(ContentType.JSON).contentType(ContentType.JSON).log().all();

    }

    @When("I hit a create company api for pojo")
    public void iHitACreateCompanyApiForPojo() {
        response = specification.post("contacts/");
    }

    @Then("I verify company created successfully with {int} status code for pojo")
    public void iVerifyCompanyCreatedSuccessfullyWithStatusCodeForPojo(int StatusCode) {
        response.prettyPrint();
        System.out.println(StatusCode);
        System.out.println(response.getStatusCode());

        Assert.assertEquals(StatusCode, response.getStatusCode());
       // response = specification.get("companies/list");

    }


//    @Then("verify company created successfully with <statusCode> status code")
//    public void verifyCompanyCreatedSuccessfullyWithStatusCodeStatusCode() {
//        System.out.println("i am running");
//    }
}


