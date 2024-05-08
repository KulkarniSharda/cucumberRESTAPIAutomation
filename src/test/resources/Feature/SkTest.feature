Feature: Verify test practice api module

  @getApi
  Scenario: verify get all company api information
    Given I setup a request structure to get all information
    When I hit an api to get all info
    Then I verify api response with valid data and statusCode 200