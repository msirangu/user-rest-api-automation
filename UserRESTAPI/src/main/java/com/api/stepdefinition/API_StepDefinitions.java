package com.api.stepdefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class API_StepDefinitions {

    private String endpoint;
    private Response response;
    private Map<String, String> userDetails;
    private String authorizationToken; // Variable to hold the authorization token
	//private static final Logger LOG = LogManager.getLogger(CreateBookingStepdefinition.class);
    private String userId;

    private String firstName;

    @Given("the API endpoint is {string}")
    public void the_api_endpoint_is(String endpoint) {
        this.endpoint = endpoint;
    }

    @Given("the Authorization token is {string}")
    public void the_authorization_token_is(String token) {
        this.authorizationToken = token; // Store the authorization token
    }
    

    @Given("I send a POST request to create a new user with the following details:")
    public void i_send_a_post_request_to_create_a_new_user_with_the_following_details(List<Map<String, String>> userDetailsList) {
        // Assuming only one user details row is sent, get the first element from the list
        this.userDetails = userDetailsList.get(0);

        // Send POST request with the user details
        response = given()
            .header("accept", "*/*")
            .header("Content-Type", "application/json")
            .header("Authorization", authorizationToken) // Add Authorization header
            .body(createRequestBody(userDetails))
            .when()
            .post(endpoint);
    }

    private String createRequestBody(Map<String, String> userDetails) {
        return String.format(
            "{\n" +
            "    \"userAddress\": {\n" +
            "        \"plotNumber\": \"%s\",\n" +
            "        \"street\": \"%s\",\n" +
            "        \"state\": \"%s\",\n" +
            "        \"country\": \"%s\",\n" +
            "        \"zipCode\": %s\n" +
            "    },\n" +
            "    \"user_first_name\": \"%s\",\n" +
            "    \"user_last_name\": \"%s\",\n" +
            "    \"user_contact_number\": \"%s\",\n" +
            "    \"user_email_id\": \"%s\",\n" +
            "    \"creation_time\": \"%s\",\n" +
            "    \"last_mod_time\": \"%s\"\n" +
            "}",
            userDetails.get("plotNumber"),
            userDetails.get("street"),
            userDetails.get("state"),
            userDetails.get("country"),
            userDetails.get("zipCode"),
            userDetails.get("user_first_name"),
            userDetails.get("user_last_name"),
            userDetails.get("user_contact_number"),
            userDetails.get("user_email_id"),
            userDetails.get("creation_time"),
            userDetails.get("last_mod_time")
        );
    }

    @When("I validate the response status code is {int}")
    public void i_validate_the_response_status_code_is(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @When("I validate the response contains the user with first name {string}, last name {string}, and email {string}")
    public void i_validate_the_response_contains_the_user_with_first_name_last_name_and_email(String firstName, String lastName, String email) {
        response.then()
            .body("user_first_name", equalTo(firstName))
            .body("user_last_name", equalTo(lastName))
            .body("user_email_id", equalTo(email));
    }

    // Get all Step to validate the status code with Authorization token
    @When("I validate the status code is {int}")
    public void validateStatusCode(int expectedStatusCode) {
        response = RestAssured.given()
                .header("Authorization", authorizationToken)
                .get(endpoint);
        response.then().statusCode(expectedStatusCode);
    }

    // Step to validate the response header
    @Then("I validate the response header contains {string} with value {string}")
    public void validateResponseHeader(String headerName, String expectedValue) {
        response.then().header(headerName, equalTo(expectedValue));
    
    }
    
    //Get id status code is 200
    

    
  // get by user id
  
//Step to capture the dynamic user ID
  @Given("the user ID is {string}")
  public void setUserId(String userId) {
      this.userId = userId;
  }

//Send the GET request with the dynamic user ID
  @When("I send a GET request to fetch the user details")
  public void sendGetRequest() {
      // Construct the complete API endpoint by appending the user ID
      String fullApiEndpoint = endpoint + "/" + userId;

      response = RestAssured.given()
              .header("Authorization", authorizationToken)  // Add Authorization header
              .header("accept", "*/*")
              .get(fullApiEndpoint);  // Use the full API endpoint with the user ID
  }


  // Step for validating the GET status code without assertEquals
  @Then("I validate the GET status code is {int}")
  public void i_validate_the_get_status_code_is(int expectedStatusCode) {
      // Using RestAssured's built-in method to check status code
      response.then().statusCode(expectedStatusCode);
  }

  // Step for validating a specific response header without assertEquals
  @Then("I validate the GET response header contains {string} with value {string}")
  public void i_validate_the_get_response_header_contains_with_value(String headerName, String expectedHeaderValue) {
      // Using RestAssured's built-in method to check the header value
      response.then().header(headerName, expectedHeaderValue);
  }
  
  
  
  // update user 
  
//Sends an update request to create a new user using the provided details
  @Given("I send an update request to create a new user with the following details:")
  public void iSendAnUpdateRequestToCreateANewUserWithTheFollowingDetails(io.cucumber.datatable.DataTable dataTable) {
      // Convert the DataTable into a map for the request body
	// Initialize the userDetails map
	 // HashMap  updateUserDetails = new HashMap<>();

      Map<String, Object> updateUserDetails = new HashMap<>();


      // Convert the DataTable into a list of maps
      List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);


      // Convert the DataTable into a list of maps

      // Loop through each row of the DataTable and populate the HashMap
      for (Map<String, String> row : rows) {
    	  
          Map<String, Object> userAddress = new HashMap<>();
          userAddress.put("addressId", Integer.parseInt(row.get("addressId"))); // Integer
          userAddress.put("plotNumber", row.get("plotNumber")); // String
          userAddress.put("street", row.get("street")); // String
          userAddress.put("state", row.get("state")); // String
          userAddress.put("country", row.get("country")); // String
          userAddress.put("zipCode", Integer.parseInt(row.get("zipCode"))); // Integer

          updateUserDetails.put("userAddress", userAddress); // Add the
  
    	  updateUserDetails.put("user_id", Integer.parseInt(row.get("user_id"))); // Integer
    	  updateUserDetails.put("user_first_name", row.get("user_first_name")); // String
          updateUserDetails.put("user_last_name", row.get("user_last_name")); // String
          updateUserDetails.put("user_contact_number", Long.parseLong(row.get("user_contact_number"))); // Long
          updateUserDetails.put("user_email_id", row.get("user_email_id")); // String
          updateUserDetails.put("creation_time", row.get("creation_time")); // String
          updateUserDetails.put("last_mod_time", row.get("last_mod_time")); // String
          

          

          // Print out the user details
          System.out.println("User Details: " + userDetails);
      }
      
      // Sending the PUT request to update the user
      response = given()
              .contentType("application/json")
              .header("Authorization", authorizationToken)
              .body(updateUserDetails)
              .when()
              .put(endpoint);
  }

  // Validates the status code of the update user response
  @When("I validate the update user response status code is {int}")
  public void iValidateTheUpdateUserResponseStatusCodeIs(int statusCode) {
      response.then().statusCode(statusCode);
  }

  // Validates that the response contains the expected user details
  @Then("I validate the update user response contains the user with first name {string}, last name {string}, and email {string}")
  public void iValidateTheUpdateUserResponseContainsTheUserWithDetails(String firstName, String lastName, String email) {
      response.then()
              .body("user_first_name", equalTo(firstName))
              .body("user_last_name", equalTo(lastName))
              .body("user_email_id", equalTo(email));
  }
  
  //delete user 
  
 

  @When("I validate the delete user response status code is {int}")
  public void iValidateTheDeleteUserResponseStatusCodeIs(int expectedStatusCode) {
      // Making the DELETE request
      response = given()
              .header("Authorization", authorizationToken)
              .when()
              .delete(endpoint + "/" + userId);
      
      // Validate the response status code
      response.then().statusCode(expectedStatusCode);
  }
  

  @Given("the user first name is {string}")
  public void setUserFirstName(String fname) {
      this.firstName = fname;
  }
  

  @When("I validate the delete user by firstname response status code is {int}")
  public void iValidateTheDeleteUserbyFirstNameResponseStatusCodeIs(int expectedStatusCode) {
      // Making the DELETE request
      response = given()
              .header("Authorization", authorizationToken)
              .when()
              .delete(endpoint + "/" + firstName);
      
      // Validate the response status code
      response.then().statusCode(expectedStatusCode);
  }
}




  


    

