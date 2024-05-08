Feature: To verify deals api

  @getDeals
  Scenario: To verify Deals api response
    Given I setup an request structure
    When I hit an api for deals
    Then I receive response of deals

  @CreateDeal
  Scenario: verify create Deal with valid details using string request body
    Given I setup request structure to create Deal with valid details
      | dealname          | Deal-Frozenxo |
      | milestoneName | Proposal      |
    When I hit a create Deal api
    Then I verify Deal created successfully

  @CreateDealWithFile
  Scenario: verify create Deal with valid details using json file as request body
    Given I setup request structure to create Deal using json file
    When I hit a create Deal api
    Then I verify Deal created successfully with 200 status code

