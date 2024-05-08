package com.agileautomation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = {"com.agileautomation.stepdefination", "com.agileautomation.common"}
        , plugin = {
        "html:target/cucumber-reports.html",
        "junit:target/cucumber-reports/Cucumber.xml"
        }
        , tags = "@GetMockData"
)

public class TestRunner {


}

