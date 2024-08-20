package io.loopcamp.test.day01_Intro;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Sample Scenario
 *
 *     Send "GET" request to API Url  --- > https://sandbox.api.service.nhs.uk/hello-world/hello/world
 *     Verify status code is 200 Ok
 *     Verify JSon Response body contains
 *             "message": "Hello Worlds!"
 */

public class HelloWorldApiTest {
    String url = "https://sandbox.api.service.nhs.uk/hello-world/hello/world";

    @DisplayName("Hello World GET request")
    @Test
    public void helloWorldGetRequestTest(){
        //send a get request and save response inside the Response object
        Response response = RestAssured.get(url);

        // Print the RESPONSE body in a formatted way (in this case in JSON format) - RESPONSE BODY
        response.prettyPrint();  //This will print and it also returns a String
        // System.out.println( response.asString() );


        // Print Status CODE related output
        System.out.println( "Response STATUS Code: " + response.statusCode() ); // 200
        System.out.println( "Response STATUS Line: " + response.statusLine() ); // HTTP/1.1 200 OK


        // assert/validate/verify that RESPONSE STATUS CODE is 200
        Assertions.assertEquals(200, response.statusCode(), "Did not match");

        // We can also assign the RESPONSE STATUS CODE into a variable and use it.
        int actualStatusCode = response.statusCode();
        Assertions.assertEquals(200, actualStatusCode, "Did not match");


        // asString(); method will return the RESPONSE BODY as a String
        String actualResponseBody = response.asString();

        // Here we validated that the RESPONSE BODY contains our expected text (Hello World!)
        Assertions.assertTrue(actualResponseBody.contains("Hello World!"));

    }

}
