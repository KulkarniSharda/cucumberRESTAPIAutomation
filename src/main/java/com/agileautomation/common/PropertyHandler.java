package com.agileautomation.common;


import java.io.*;
import java.util.Properties;


public class PropertyHandler {

    Properties properties;

    public PropertyHandler(String fileName) {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/properties/" + fileName;
        try {
//read the file
            InputStream file = new FileInputStream(filePath);
//create the instance of properties class
            properties = new Properties();
//load the file into properties class
            properties.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}

