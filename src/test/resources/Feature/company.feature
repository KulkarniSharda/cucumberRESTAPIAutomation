Feature: verify crud operations of company module

  @getComapny
  Scenario: verify get all company api information
    Given I setup a request structure to get all company information
      | method |
      | POST   |
    When I hit an api to get all company information
    Then I verify company response

    * I get name of all companies
    * I get name of company which tags are not empty

  @GetQuery
  Scenario: verify search feature for company
    Given I setup a request structure to get all company info with below keyword
      | q         | Allein  |
      | page_size | 10      |
      | type      | COMPANY |
    When I hit an api to search company with keywords
    Then I verify the result according to keywords

  @CreateCompany
  Scenario: verify create company with valid details using string request body
    Given I setup request structure to create company with valid details
      | compName | Allein                |
      | url      | http://www.allein.com |
    When I hit a create company api
    Then I verify company created successfully

  @CreateCompany1
#    Scenario try to verify
  Scenario: verily create company With valid details using Hashmap request body
    Given I setup request structure to create company with hashmap object
      | compName | CompUrl                  |
      | Alleza   | https://cybersuccess.biz |
    When I hit a create company api
    Then I verify company created successfully with 200 status code


  @CreateCompany2
  Scenario Outline:Verify Create company with valid Details Using hashmap request body
    Given I setup request structure to create company with hashmap object
      | compName | CompUrl |
      | <name>   | <url>   |
    When I hit a create company api
    Then I verify company created successfully with <StatusCode> status code
    Examples:
      | name   | url                      | StatusCode |
      | Cyber  | https://cybersuccess.biz | 200        |
      | Alleza |                          | 400        |
      |        |                          | 400        |
      |        | https://cybersuccess.biz | 400        |
      | null   | https://cybersuccess.biz | 200        |
      | Alleza | null                     | 200        |

  @CreateCompanyWithFile
  Scenario: verify create company with valid details using json file as request body
    Given I setup request structure to create company using json file
    When I hit a create company api
    Then I verify company created successfully with 200 status code

  @CreateCompanywithPojo
  Scenario: verify create company with valid details using pojo class
    Given I setup request structure to create company using pojo class
    When I hit a create company api for pojo
    Then I verify company created successfully with 200 status code for pojo

