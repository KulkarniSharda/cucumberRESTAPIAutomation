package com.agileautomation.stepdefination;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;

import static java.nio.file.Files.readAllBytes;

public class FileUploadDownloadStepdefination {
    RequestSpecification specification;
    Response response;
    File file;

    @Given("I setup request structure to upload the file")
    public void setupRequestUploadFie() {
        String filePath = "G:\\TestData.xlsx";
        file = new File(filePath);
        specification = RestAssured.given();
        specification.baseUri("https://postman-echo.com")
                .basePath("/post").contentType(ContentType.MULTIPART)
                .multiPart("file", file)
                .log().all();
    }

    @When("I hit an api to upload the file")
    public void iHitAnApiToUploadTheFile() {
        response = specification.post();
    }

    @Then("I verify file is getting uploaded successfully")
    public void iVerifyFileIsGettingUploadedSuccessfully() {
        response.prettyPrint();
        Map<String, String> fileObject = response.jsonPath().getMap("files");
        Assert.assertTrue(fileObject.keySet().contains(file.getName()));

    }

    @Given("I setup request structure to download the file")
    public void iSetupRequestStructureToDownloadTheFile() {
        specification = RestAssured.given();
        specification.baseUri("https://raw.githubusercontent.com")
                .basePath("Harshad-Dange/actitime_apiautomation/main")
                .accept(ContentType.ANY)
                .log().all();
    }

    @When("I hit an api to download the file")
    public void iHitAnApiToDownloadTheFile() {
        response = specification.get("/pom.xml");
    }

    @Then("I verify file is getting downloaded successfully")
    public void iVerifyFileIsGettingDownloadedSuccessfully() throws IOException {
        response.prettyPrint();
        InputStream inputStream = response.asInputStream();
//create a file
        FileOutputStream outputStream = new FileOutputStream("G:\\pom.xml");
//write a content into file
        //outputStream.write(inputStream, readAllBytes(""));
        //outputStream.write(inputStream,readAllBytes(Path.of("")));
       // outputStream.flush();
       // outputStream.close();
    }

    @Then("I verify file is getting downloaded successfully using byteStream")
    public void iVerifyFileIsGettingDownloadedSuccessfullyUsingByteStream() throws IOException {
        response.prettyPrint();
        byte[] byteArray = response.asByteArray();
        FileOutputStream outputStream = new FileOutputStream("G:\\pom.xml");
//write a content into file
        outputStream.write(byteArray);
        outputStream.flush();
        outputStream.close();
    }
}

