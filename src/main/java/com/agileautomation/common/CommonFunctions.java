package com.agileautomation.common;
import io.restassured.http.Method;
import io.restassured.response. Response;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.Map;

public class CommonFunctions
{
    ApiRequestBuilder requestBuilder= new ApiRequestBuilder();

        public Response getResponse(String endpoint){
             requestBuilder= new ApiRequestBuilder();
            requestBuilder.setupRequestConfigs();
            requestBuilder.execute(Method.GET, endpoint);
            return requestBuilder.response;
        }
      public Response getResponseFromMock(String endpoint){
        requestBuilder.setupRequestConfigsForMockServer();
        requestBuilder.execute(Method.GET, endpoint);
        return  requestBuilder.response;
    }
        public Response getResponseWithQueryParams(Map<String, String> queryParams, String endpoint){
          requestBuilder= new ApiRequestBuilder();
            requestBuilder.setupRequestConfigs();
            requestBuilder.setQueryParams(queryParams);
            requestBuilder.execute(Method.GET, endpoint);
            return requestBuilder.response;
        }
    public Response getResponseWithPathParam(String endpoint, String pathParam){

        requestBuilder.setupRequestConfigs();
        requestBuilder.setPathParam(pathParam);
        requestBuilder.execute(Method.GET, endpoint);
        return  requestBuilder.response;
    }

    public Response postWithBody(File fileBody, String endpoint){
        requestBuilder.setupRequestConfigs();
        requestBuilder.setRequestBody(fileBody);
        requestBuilder.execute(Method.POST, endpoint);
        return  requestBuilder.response;
    }
    public Response postWithBody(String stringBody, String endpoint){
        requestBuilder.setupRequestConfigs();
        requestBuilder.setRequestBody(stringBody);
        requestBuilder.execute(Method.POST, endpoint);
        return  requestBuilder.response;
    }

    public Response postWithBody(JSONObject jsonObject, String endpoint){
        requestBuilder.setupRequestConfigs();
        requestBuilder.setRequestBody(jsonObject);
        requestBuilder.execute(Method.POST, endpoint);
        return  requestBuilder.response;
    }

    public Response postWithBody(Object object, String endpoint){
        requestBuilder.setupRequestConfigs();
        requestBuilder.setRequestBody(object);
        requestBuilder.execute(Method.POST, endpoint);
        return  requestBuilder.response;
    }

    }
