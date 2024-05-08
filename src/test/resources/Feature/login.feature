Feature: verify login feature for application
  Scenario: verify login functionality with valid and invalid credentials
  Given I setup request structure to login into application
  When I hit an api to setup login
  Then I verify user login successfully




#    Scenario Outline:
#      Examples:
#        |  |
