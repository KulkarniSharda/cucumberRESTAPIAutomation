Feature: verify contact api's

  @GetContactList

  Scenario: verify get all contact api response
    Given I setup a request structure to get all contacts
    When I hit an get all contact api
    Then I verify all contacts in response

  @CreateContact
  Scenario: verify create contact with valid details using string request body
    Given I setup request structure to create contact with valid details
      | firstName | Sizuka  |
      | lastName  | Bustlin |
    When I hit a create contact api
    Then I verify contact created successfully

  @CreateContact1
#    Scenario try to verify
  Scenario: verily create contact With valid details using Hashmap request body
    Given I setup request structure to create contact with hashmap object
      | firstName | lastName |
      | Cristi    | metiv    |
    When I hit a create contact api
    Then verify contact created successfully with 201 status code

  @CreateContact2
  Scenario Outline:Verify Create contact with valid Details Using hashmap request body
    Given I setup request structure to create contact with hashmap object
      | firstName   | lastName   |
      | <firstName> | <lastName> |
    When I hit a create contact api
    Then verify contact created successfully with <statusCode> status code
    Examples:
      | firstName | lastName | statusCode |
      | Critin    | Martin   | 200        |
      | Alliena   |          | 400        |
      |           | Rozes    | 400        |
      |           |          | 400        |
      | null      | Morencel | 200        |
      | Allina    | null     | 200        |

  @CreateContactWithFile
  Scenario: verify create contact with valid details using json file as request body
    Given I setup request structure to create contact using json file
    When I hit a create contact api
    Then  verify contact created successfully with 200 status code


  @CreateContactwithPojo
  Scenario: verify create Contact with valid details using pojo class
    Given I setup request structure to create Contact using pojo class
    When I hit a create contact api for pojo
    Then  verify contact created successfully with 200 status code for pojo


  @GetContactUsingSeriandDesrilization
  Scenario: verify contact by id using serialization and deserialization
    Given I setup request structure to get contact using serialization
    When I hit a get contact api for pojo serialization
    Then I verify contact by id successfully using deserialization


  @SortingContacts
  Scenario: verify sorting in descending order
    Given I setup request structure to get all users info using dynamic filters
    When I hit an api to get the response
    Then I verify response is getting sorted in descending order

  @GetContactListwithFramework
  Scenario: verify get all contact api response
    Given I get contacts information
    Then I verify all contacts in response


#    6453695683035136 id {
#    "properties": [
#        {
#            "type": "SYSTEM",
#            "name": "first_name",
#            "value": "AZESHN"
#        },
#        {
#            "type": "SYSTEM",
#            "name": "last_name",
#            "value": "WRON"
#        }
#]}