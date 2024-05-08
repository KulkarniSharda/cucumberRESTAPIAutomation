Feature: Verify reqres api's

  @ReqRes1
  Scenario: Verify get all users info
    Given I setup a request structure to get all users information
    When I hit an get all users api
    Then I verify all users response

  @ReqRes2
  Scenario: Verify total user records on all pages
    Given I setup a request structure to get all users information
    When I hit an get all users api
    Then I verify total users in response

#     SERIALIZATION AND DESERIALIZATION SCENARIOS
  @ReqResCreateUserPojo
  Scenario: verify total user records on all pages
    Given I setup request structure to create users
    When I hit a api to create user
    Then I verify user created successfully

  @Deserialization
  Scenario: verify get all users info using deserialization
    Given I setup a request structure to get all users information
    When I hit an get all users api
    Then I verify all users response using deserialization

  @CreateUserUsingSeriandDesrilization
  Scenario: verify user creation using serialization and deserialization
    Given I setup request structure to create users using serialization
    When I hit a api to create user
    Then I verify user created successfully using deserialization

  @GetMockData
  Scenario: Verify get all users info
    Given I setup a request structure to get all users information
    When I hit an get all users api
    Then I verify all users response