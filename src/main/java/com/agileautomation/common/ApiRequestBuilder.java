package com.agileautomation.common;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.Map;


public class ApiRequestBuilder {

    RequestSpecification specification;
    Response response;
//setup request structure

    public void setupRequestConfigs() {
        specification = RestAssured.given();
        PropertyHandler propertyHandler = new PropertyHandler("config.properties");
        String baseUri = propertyHandler.getProperty("baseUri");
        String basePath = propertyHandler.getProperty("basePath");
        String token = propertyHandler.getProperty("token");
        specification.baseUri(baseUri).basePath(basePath).header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON).log().all();
    }
    public void setupRequestConfigsForMockServer(){
        specification = RestAssured.given();
        PropertyHandler propertyHandler = new PropertyHandler("config.properties");
        String baseUri= propertyHandler.getProperty("mockBaseUri");
        specification.baseUri(baseUri)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();
    }
    public void execute(Method method, String endpoint) {
        PropertyHandler propertyHandler = new PropertyHandler("endpoints.properties");
        switch (method) {
            case GET:
                response = specification.get(propertyHandler.getProperty(endpoint)+ getParam());
                break;
            case POST:
                response = specification.post(propertyHandler.getProperty(endpoint)+ getParam());
                break;
            case PUT:
                response = specification.put(propertyHandler.getProperty(endpoint) + getParam());
                break;
            case PATCH:
                response = specification.patch(propertyHandler.getProperty(endpoint) + getParam());
                break;
            case DELETE:
                response = specification.delete(propertyHandler.getProperty(endpoint)+ getParam());
                break;
        }
    }

    public void setHeaders(Map<String,String>headers) {
        if (headers!= null && headers.isEmpty()) {
            specification.header((Header) headers);
        }
    }

    public void setQueryParams(Map<String, String> queryParams) {
        if (queryParams != null && !queryParams.isEmpty())
            specification.queryParams(queryParams);
    }
    public void setPathParam(String pathParam){
        if(pathParam != null && !pathParam.isEmpty()){
            specification.pathParam("param", pathParam );
        }

    }
    public void setRequestBody(String body) {
        if (!body.isEmpty()) {
            specification.body(body);
        }
    }

    public void setRequestBody(JSONObject requestBody) {
        if (requestBody != null)
            specification.body(requestBody);
    }

    public void setRequestBody(File file) {
        if(file != null) {
            specification.body(file);
        }
    }
    public void setRequestBody(Object object){
        if(object!= null){
            specification.body(object);
        }
    }
    public String getParam(){

        String parameter = SpecificationQuerier.query(specification).getPathParams().get("param");

        if( parameter != null && !parameter.isEmpty()){
            return "/{param}";
        }else {
            return"" ;
        }


    }
}