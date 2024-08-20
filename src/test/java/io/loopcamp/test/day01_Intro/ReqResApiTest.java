package io.loopcamp.test.day01_Intro;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * When user sends GET request to
 *      https://reqres.in/api/users/
 *-------------------------------------------------
 * Then RESPONSE STATUS CODE is 200
 * And RESPONSE BODY should contain "George"
 * And RESPONSE Headers Content Type is JSON
 */
public class ReqResApiTest {

    String endpoint = "https://reqres.in/api/users/";

    @DisplayName("GET all users")
    @Test
    public void getUsersTest (){

        // We sent GET request to the ENDPOINT and stored the whole RESPONSE into Response response
        Response response = RestAssured.get(endpoint);

        System.out.println("RESPONSE STATUS: " + response.statusCode());
        Assertions.assertEquals(200, response.statusCode());

        // BDD approach - assertions
        response.then().statusCode(200); // this is doing exact same thing as one above
        response.then().assertThat().statusCode(200); // this is doing exact same thing as one above



        // And RESPONSE BODY should contain "George"

        // DIFFERENCE between .prettyPrint() vs .asString()
        // .prettyPrint() --- > print as in formatted way
        // .asString --- > print as in ONE LINE
        // response.prettyPrint();
        // System.out.println(response.asString());

        assertTrue(response.asString().contains("George"));


        // And RESPONSE Headers Content Type is JSON
        System.out.println( "Response HEADER CONTENT TYPE: "  + response.contentType() );
        assertTrue(response.contentType().contains("application/json"));


    }


    /** NEW TEST CASE
     When User Sends get request to API Endpoint:
     "https://reqres.in/api/users/5"
     ---------------------------------------------------------
     Then Response status code should be 200
     And Response body should contain user info "Charles"
     And Response Header's Content Type is application/json
     */

    String endpoint2 = "https://reqres.in/api/users/5";
    @DisplayName("GET single user")
    @Test
    public void getSingleUserTest (){

        // Response response = RestAssured.get(endpoint2);
        // with BDD approach - it does the same thing as above
        Response response = when().get(endpoint2);


        // Assertions.assertEquals(200, response.statusCode());
        // with BDD approach - it does the same thing as above
        response.then().assertThat().statusCode(200);


        // response.prettyPrint();
        assertTrue(response.body().asString().contains("Charles"));


        // System.out.println( "Response Content Type: " + response.contentType());
        assertTrue(response.contentType().contains("application/json"));


    }





    /** NEW TEST CASE
     When User Sends get request to API Endpoint:
     "https://reqres.in/api/users/50"
     ---------------------------------------------------------
     Then Response status code should be 404
     And Response body should contain {}
     */

    @DisplayName("GET single user negative test")
    @Test
    public void getSingleUserNegativeTest (){

        Response response = RestAssured.get(endpoint + 50);

        // Since we did static import for all (*), we do not need to use the class name to call the static methods
        assertEquals(404, response.statusCode());

        // response.prettyPrint();
        // System.out.println(response.asString());

        assertTrue(response.body().asString().contains("{}"));
        assertEquals("{}", response.body().asString());

    }

}