Feature: To verify Task api

  @getTasks
  Scenario: To verify Task api response
    Given I setup an request structure for Task
    When I hit an api for Task
    Then I receive response of Task

  @CreateTask
  Scenario: verify create Task with valid details using string hashmap body
    Given I setup request structure to create Task with valid details
      | type    | status       | priority_type |
      | FOLLOW_UP | COMPLETED | HIGH          |
    When I hit a create Task api
    Then I verify Task created successfully

  @CreateTaskWithFile
  Scenario: verify create Task with valid details using json file as request body
    Given I setup request structure to create Task using json file
    When I hit a create Task api
    Then I verify Task created successfully with 200 status code

#  @filterTasksDyanmicfilter
#  Scenario: To verify Task api response by applying dynamic filter
#    Given I setup an request structure for Task by filter
#    When I hit an api for Task
#    Then I receive response of Task



# progress (0 to 100), is_complete (true or false), type (CALL, EMAIL, FOLLOW_UP, MEETING, MILESTONE, SEND, TWEET, OTHER),
# priority_type (HIGH, NORMAL, LOW), status (YET_TO_START, IN_PROGRESS, COMPLETED)
#  {
#  "progress": "0",
#  "is_complete": "false",
#  "subject": "Need to contact vendor",
#  "type": "MEETING",
#  "priority_type": "HIGH",
#  "status": "YET_TO_START",
#  "TaskDescription": "This is very important. We need to discuss with few vendors about the product."
#
#  }
#
