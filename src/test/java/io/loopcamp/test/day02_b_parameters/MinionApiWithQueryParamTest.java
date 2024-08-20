package io.loopcamp.test.day02_b_parameters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionApiWithQueryParamTest {

    /**
     Given Accept type is Json
     And query parameter values are:
     gender|Female
     nameContains|e
     When user sends GET request to /api/minions/search
     ---------------------------
     Then response status code should be 200
     And response content-type: application/json
     And "Female" should be in response payload
     And "Janette" should be in response payload
     */

    //String endpoint = "http://34.239.134.73:8000/api/minions/search";
    String endpoint = "http://3.89.221.22:8000/api/minions/search";
    @DisplayName("GET /api/minions/search with Query params")
    @Test
    public void searForMinionTest () {

        /*
        // OPTION 1
        // Since we have not learned how to validate the body as part of the chaining we will to the OPTION 2
        given().accept(ContentType.JSON)
                .and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "e")
                .when().get(endpoint)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON);
         */

        /*
        // OPTION 2
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "e")
                .when().get(endpoint);
         */


        /*
        // OPTION 3
        Response response = given().accept(ContentType.JSON)
                .and().queryParams("gender", "Female", "nameContains", "e")
                .when().get(endpoint);
         */


        // OPTION 4
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("gender", "Female");
        paramsMap.put("nameContains", "e");

        Response response = given().accept(ContentType.JSON)
                .and().queryParams(paramsMap)
                .when().get(endpoint);

        // assert response Status Code
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        // assert response Content Type
        assertEquals(ContentType.JSON.toString(), response.contentType());

        // And "Female" should be in response payload
        // And "Janette" should be in response payload
        assertTrue( response.body().asString().contains("Female") );
        assertTrue( response.body().asString().contains("e"));

        // response.prettyPrint();

    }

}

