package com.agileautomation.stepdefination;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepdefination {
    @Given("I setup request structure to login into application")
    public void setupRequest(){
        System.out.println("Login Setup");

    }


    @When("I hit an api to setup login")
    public void iHitAnApiToSetupLogin() {
        System.out.println("hit api ");
    }

    @Then("I verify user login successfully")
    public void iVerifyUserLoginSuccessfully() {
        System.out.println("verify user then login successfully");
    }
}
