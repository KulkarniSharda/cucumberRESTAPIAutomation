package com.agileautomation.stepdefination;

import com.agileautomation.common.CommonFunctions;
import com.agileautomation.pojo.CreateContactPojo;
import com.agileautomation.pojo.CreateContactResponsePojo;
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

//https://automationagile.agilecrm.com/dev/api/contacts id  6453695683035136
public class ContactStepdefination {
    RequestSpecification specification;
    Response response;
    String firstName;
    String lastName;
    String requestBody;
    CreateContactResponsePojo responsePojo;
    Map<String,String> queryParams = new HashMap<>();
    CommonFunctions commonFunctions = new CommonFunctions();

    @Given("I setup a request structure to get all contacts")
    public void setupRequestStructure() {
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com")
                .basePath("/dev/api/")
                .auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Accept", "application/json")
                .log().all();
    }

    @When("I hit an get all contact api")
    public void iHitAnGetAllContactApi() {
        response = specification.get("/contacts");
    }

    @Then("I verify all contacts in response")
    public void iVerifyAllContactsInResponse() {
        response.prettyPrint();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);

        List<Object> responseList = response.jsonPath().getList("");
        System.out.println(responseList);
        for (Object contactList : responseList) {
            Map<String, Object> objList = (Map<String, Object>) contactList;
            System.out.println(objList);

            Object propObject = objList.get("properties");
            System.out.println(propObject);

            List<Object> propList = (List<Object>) propObject;
            System.out.println(propList);
            for (Object obj : propList) {
                Map<String, String> mapList = (Map<String, String>) obj;
                System.out.println(mapList);

                if (mapList.get("name").equals("last_name")) {
                    String name = mapList.get("value");
                    String id = objList.get("id").toString();
                    System.out.println(id + ":" + name);

                }
            }

        }

    }

    @Given("I setup request structure to create contact with valid details")
    public void iSetupRequestStructureToCreateContactWithValidDetails(Map<String, String> contData) {
        {
            firstName = contData.get("firstName");
            lastName = contData.get("lastName");

            requestBody = "{\n" +
                    "    \"properties\": [\n" +
                    "        {\n" +
                    "            \"type\": \"SYSTEM\",\n" +
                    "            \"name\": \"first_name\",\n" +
                    "            \"value\": \"" + firstName + "\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \"SYSTEM\",\n" +
                    "            \"name\": \"last_name\",\n" +
                    "            \"value\": \"" + lastName + "\"\n" +
                    "        }\n" +
                    "]}";
            specification = RestAssured.given();
            specification.baseUri("https://automationagile.agilecrm.com")
                    .basePath("/dev/api")
                    .auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                    .header("Accept", ContentType.JSON).header("Content-Type", "application/json")
                    .body(requestBody)
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .log().all();
        }


    }

    @When("I hit a create contact api")
    public void iHitACreateContactApi() {
        response = specification.post("/contacts");

    }

    @Then("I verify contact created successfully")
    public void iVerifyContactCreatedSuccessfully() {
        response.prettyPrint();
        //5063481660080128 id
        Assert.assertEquals(200, response.getStatusCode());
        List<Map<String, String>> propList = response.jsonPath().getList("properties");
        for (Map<String, String> propObject : propList) {
            if (propObject.get("name").equals("name")) {
                String actualFirstName = propObject.get("value");
                Assert.assertEquals(firstName, actualFirstName);
            } else if (propObject.get("name").equals("name")) {
                String actualLastName = propObject.get("value");
                Assert.assertEquals(lastName, actualLastName);
            } else {
                System.out.println("I am out of condition");
            }

        }
    }

    @Given("I setup request structure to create contact with hashmap object")
    public void iSetupRequestStructureToCreateContactWithHashmapObject(DataTable table) {
        {
            List<Map<String, String>> testData = table.asMaps();
            Map<String, String> contData = testData.get(0);
            firstName = contData.get("firstName");
            lastName = contData.get("lastName");
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("type", "PERSON");
            List<Map<String, String>> propList = new ArrayList<>();
            Map<String, String> map1 = new HashMap<>();
            map1.put("name", "Comapny Type");
            map1.put("type", "CUSTOM");
            map1.put("value", "MNC Inc");

            propList.add(map1);

            Map<String, String> map2 = new HashMap<>();
            map2.put("type", "SYSTEM");
            map2.put("name", "firstName");

            Map<String, String> map3 = new HashMap<>();
            map3.put("type", "SYSTEM");
            map3.put("name", "lastName");

            if (firstName == null) {
                map2.put("value", null);
                map3.put("value", lastName);
            } else if (lastName == null) {
                map2.put("value", firstName);
                map3.put("value", null);
            } else if (firstName == null && lastName == null) {
                map2.put("value", null);
                map3.put("value", null);
            } else {
                map2.put("value", firstName);
                map3.put("value", lastName);
            }

            propList.add(map2);

            propList.add(map3);

            requestBody.put("properties", propList);
            {
                specification = RestAssured.given();
                specification.baseUri("https://apiautomation.agilecrm.com")
                        .basePath("/dev/api")
                        .auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")

                        //.header("Content", ContentType.JSON)
                        // .header("Accept", ContentType.JSON)
                        .body(requestBody)
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .log().all();

            }
        }

    }

    @Then("verify contact created successfully with {int} status code")
    public void verifyContactCreatedSuccessfullyWithStatusCode(int StatusCode) {
        response.prettyPrint();
        Assert.assertEquals(StatusCode, response.getStatusCode());
        List<Map<String, String>> propList = response.jsonPath().getList("properties");
        for (Map<String, String> propboject : propList) {
            if (propboject.get("name").equals("name")) {
                String actualfirstName = propboject.get("value");
                Assert.assertEquals(firstName, actualfirstName);
            } else if (propboject.get("name").equals("name")) {
                String actualUrl = propboject.get("value");
                Assert.assertEquals(lastName, actualUrl);

            }
        }
    }

    @Given("I setup request structure to create contact using json file")
    public void iSetupRequestStructureToCreateContactUsingJsonFile() throws IOException, ParseException {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/RequestFiles/CreateContactRequest.json";
        File file = new File(filePath);
//read the content of the file
        FileReader fileReader = new FileReader(file);
//create an instance of Parser
        JSONParser jsonParser = new JSONParser();
        Object inputObject = jsonParser.parse(fileReader); //read content line/line
        JSONObject jsonObject = (JSONObject) inputObject;
        JSONArray propArray = (JSONArray) jsonObject.get("properties");
        JSONObject firstNameObject = (JSONObject) propArray.get(0);
        firstName = firstNameObject.get("value").toString();
        JSONObject lastNameObject = (JSONObject) propArray.get(1);
        lastName = lastNameObject.get("value").toString();
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("/dev/api")
                .auth()
                .basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Accept", ContentType.JSON).header("Content-Type", "application/json")
                .body(file)
                //.body(jsonObject)
                .accept(ContentType.JSON).contentType(ContentType.JSON).log().all();
        response = specification.post("/contacts");
        System.out.println("response" + response.prettyPrint());
    }

    @Given("I setup request structure to create Contact using pojo class")
    public void iSetupRequestStructureToCreateContactUsingPojoClass() {

        CreateContactPojo userPojo = new CreateContactPojo();
        userPojo.setFirstName("Harry");
        userPojo.setLastName("Potter");
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("/dev/api")
                .auth()
                .basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Accept", ContentType.JSON).header("Content-Type", "application/json").
                body(userPojo)
                .accept(ContentType.JSON).contentType(ContentType.JSON).log().all();


    }

    @When("I hit a create contact api for pojo")
    public void iHitACreateContactApiForPojo() {
        response = specification.post("/contacts");
    }

    @Then("verify contact created successfully with {int} status code for pojo")
    public void verifyContactCreatedSuccessfullyWithStatusCodeForPojo(int statusCode) {
        response.prettyPrint();
        Assert.assertEquals(200, response.getStatusCode());
        List<Map<String, String>> propList = response.jsonPath().getList("properties");
        for (Map<String, String> propboject : propList) {
            if (propboject.get("name").equals("name")) {
                String actualfirstName = propboject.get("value");
                Assert.assertEquals(firstName, actualfirstName);
            } else if (propboject.get("name").equals("name")) {
                String actualUrl = propboject.get("value");
                Assert.assertEquals(lastName, actualUrl);

            }
        }
        System.out.println("Name" + firstName);
        System.out.println("LN" + lastName);
    }

    @Given("I setup request structure to create contact using serialization")
    public void iSetupRequestStructureToCreateContactUsingSerialization() {
        CreateContactPojo contactPojo = new CreateContactPojo();

        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("/dev/api")
                .auth()
                .basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Accept", ContentType.JSON).header("Content-Type", "application/json").
                body(contactPojo)
                .accept(ContentType.JSON).contentType(ContentType.JSON).log().all();

    }


    @Given("I setup request structure to get contact using serialization")
    public void iSetupRequestStructureToGetContactUsingSerialization() {
        specification = RestAssured.given();
        specification.baseUri("https://automationagile.agilecrm.com").basePath("/dev/api")
                .auth()
                .basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Accept", "application/json").header("Content-Type", "application/json")
                .accept(ContentType.JSON).contentType(ContentType.JSON).log().all();


    }

    @When("I hit a get contact api for pojo serialization")
    public void iHitAGetContactApiForPojoSerialization() {
        response = specification.get("/contacts/6453695683035136");
    }

    @Then("I verify contact by id successfully using deserialization")
    public void iVerifyContactByIdSuccessfullyUsingDeserialization() {
        response.prettyPrint();
        Assert.assertEquals(200, response.getStatusCode());

        CreateContactResponsePojo responsePojo = response.as(CreateContactResponsePojo.class);

        CreateContactPojo contactPojo = new CreateContactPojo();
        String expectedFirstName = contactPojo.getFirstName();
        String actualFirstName = responsePojo.getFirstName();
        Assert.assertEquals(expectedFirstName, actualFirstName);
        /*String expectedLastName = contactPojo.getLastName();
        String actualLastName = responsePojo.getLastName();
        Assert.assertEquals(expectedLastName, actualLastName);
        Long expectedid = response.jsonPath().getLong("id");
        Long actualid = responsePojo.getId();
        Assert.assertEquals(expectedid, actualid);*/


    }

    @Given("I setup request structure to get all users info using dynamic filters")
    public void iSetupRequestStructureToGetAllUsersInfoUsingDynamicFilters() {
        Map<String, String> formBody = new HashMap<>();
        formBody.put("page_size", "10");
        formBody.put("global_sort_key", "-created_time");
        formBody.put("filterJson", "{\"contact_type\": \"PERSON\"}");

        specification = RestAssured.given();
        specification.baseUri("http://agileautomation.agilecrm.com")
                .basePath("/dev/api/filters/filter").
                header("Content-Type", ContentType.URLENC).
                formParams(formBody).
                auth().basic("automationagile@yopmail.com", "9jissj53ko8khnppfi2r0432ii")
                .header("Accept", "application/json").
                log().all();

    }

    @When("I hit an api to get the response")
    public void iHitAnApiToGetTheResponse() {
        response = specification.post("/dynamic-filter");
    }

    @Then("I verify response is getting sorted in descending order")
    public void iVerifyResponseIsGettingSortedInDescendingOrder() {
        response.prettyPrint();
        Assert.assertEquals(200, response.getStatusCode());
        List<Integer> timeList = response.jsonPath().getList("created_time");
        System.out.println(timeList);
        List<Integer> expectedTimeList = timeList;
        Comparator<Integer> descedingComp = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;

            }
        };

        Collections.sort(expectedTimeList, Collections.reverseOrder(descedingComp));
        System.out.println("DescOrder :" + expectedTimeList);
        for (int i = 0; i < expectedTimeList.size(); i++) {
            Assert.assertEquals(expectedTimeList.get(i), timeList.get(i));
        }

    }

    @Given("I get contacts information")
    public void iGetContactsInformation() {
        CommonFunctions commonFunctions = new CommonFunctions();
        response = commonFunctions.getResponse("contact");
    }

    @Given("I get all contacts with page size query param")
    public void iGetAllContactsWithPageSizeQueryParam() {

        queryParams.put("page_size", "2");
        response = commonFunctions.getResponseWithQueryParams(queryParams, "contact");

    }

}





