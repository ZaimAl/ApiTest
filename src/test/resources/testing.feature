@API
Feature: API Testing
  @API-test1
  Scenario: user GET list from reqres in page one and two
    Given user have an API to access "https://reqres.in/api/users?page="
    And user input to get page third (1) with per_page (4)
    When user send a GET respond per page
    Then respond code should be (200)
    And  user GET API respond page (1) with per_page (4)

  @API-test2
  Scenario: user input POST to change name
    Given user have an API to access "https://reqres.in/api/users"
    And user give name as "updateUser123"
    When user send a post respond
    Then respond code should be (200)
    And  respond should be name "updateUser123"

  @API-test3
  Scenario: try to DELETE user in database
    Given user have an API to access "https://reqres.in/api/users"
    And user get the id number (2)
    When user send a DELETE respond
    Then respond code should be (204)
