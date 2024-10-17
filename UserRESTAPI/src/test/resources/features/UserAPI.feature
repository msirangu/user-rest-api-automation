
 Feature: User API Testing with Data-Driven
  This feature tests the User API with data-driven examples to create new users and validate responses.
  @userCreateAPI
  Scenario: Create a new user and validate the response
    Given the API endpoint is "https://userserviceapp-f5a54828541b.herokuapp.com/uap/createusers"
    And the Authorization token is "Basic TnVtcHlAZ21haWwuY29tOnVzZXJhcGlAb2N0b2Jlcg=="
    And I send a POST request to create a new user with the following details:
      | plotNumber    | street     | state | country | zipCode | user_first_name | user_last_name | user_contact_number | user_email_id           | creation_time       | last_mod_time      |
      | 102-AB           | village    | CA    | USA     | 95671   | veenavv           | papavv            | 2200051229       | veenapapavv@gmail.com | 2024-09-20T17:50:39.297+00:00   | 2024-09-20T17:50:39.297+00:00   |
    When I validate the response status code is 201
    And I validate the response contains the user with first name "veenavv", last name "papavv", and email "veenapapavv@gmail.com"
    
   # Validate API response for Get All Users endpoint
 @userGetALLAPI
  Scenario: Validate status code for the Get All Users endpoint
    Given the API endpoint is "https://userserviceapp-f5a54828541b.herokuapp.com/uap/users"
    And the Authorization token is "Basic TnVtcHlAZ21haWwuY29tOnVzZXJhcGlAb2N0b2Jlcg=="
    When I validate the status code is 200
    Then  I validate the response header contains "Content-Type" with value "application/json"


 @userGetbyUserID
  Scenario: Validate status code 200 for valid user ID
    Given the API endpoint is "https://userserviceapp-f5a54828541b.herokuapp.com/uap/user"
    And the user ID is "10136"
    And the Authorization token is "Basic TnVtcHlAZ21haWwuY29tOnVzZXJhcGlAb2N0b2Jlcg=="
    When I send a GET request to fetch the user details
    Then I validate the GET status code is 200
    And I validate the GET response header contains "Content-Type" with value "application/json"
    
  @updateUser
  Scenario: Update user and validate the response
    Given the API endpoint is "https://userserviceapp-f5a54828541b.herokuapp.com/uap/updateuser/10125"
    And the Authorization token is "Basic TnVtcHlAZ21haWwuY29tOnVzZXJhcGlAb2N0b2Jlcg=="
    And I send an update request to create a new user with the following details:
      | addressId    | plotNumber     | street | state | country | zipCode | user_id | user_first_name | user_last_name           | user_contact_number       | user_email_id      | creation_time | last_mod_time
      | 10093           | P1-11    | Main    | LA     | USA   | 20878  | 10125  | lavanya | John | 4499934456  | Team03LavaJohn@gmail.com | 2024-10-14T15:11:14.527+00:00 | 2024-10-14T15:11:14.527+00:00
    When I validate the update user response status code is 200
    And I validate the update user response contains the user with first name "lavanya", last name "John", and email "Team03LavaJohn@gmail.com"
    
@deleteUserbyID
  Scenario: delete user by id and validate the response
    Given the API endpoint is "https://userserviceapp-f5a54828541b.herokuapp.com/uap/deleteuser"
    And the Authorization token is "Basic TnVtcHlAZ21haWwuY29tOnVzZXJhcGlAb2N0b2Jlcg=="
    And the user ID is "10126"
    When I validate the delete user response status code is 200
    
    
 @deleteUserbyFirstName
  Scenario: delete user by firstname and validate the response
    Given the API endpoint is "https://userserviceapp-f5a54828541b.herokuapp.com/uap/deleteuser/username"
    And the Authorization token is "Basic TnVtcHlAZ21haWwuY29tOnVzZXJhcGlAb2N0b2Jlcg=="
    And the user first name is "Ramuc"
    When I validate the delete user by firstname response status code is 200