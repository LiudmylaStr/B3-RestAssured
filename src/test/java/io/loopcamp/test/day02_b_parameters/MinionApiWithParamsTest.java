package io.loopcamp.test.day02_b_parameters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionApiWithParamsTest {

    /**
     Given accept type is Json
     And Id path parameter value is 5
     When user sends GET request to /api/minions/{id}
     ------------------------
     Then response status code should be 200
     And response content-type: application/json
     And "Blythe" should be in response payload(body)
     */

    //String apiEndpoint = "http://34.239.134.73:8000/api/minions"; // here should be my IP, this is Feyrus"s
    String apiEndpoint = "http://3.89.221.22:8000/api/minions";
    @DisplayName("GET /api/minions/{id}")
    @Test
    public void getSingleMinionTest () {

        // OPTION 1
        given().accept(ContentType.JSON)
                .when().get(apiEndpoint + "/5");

        // OPTION 2
        int id = 5;
        given().accept(ContentType.JSON)
                .when().get(apiEndpoint + "/" + id);


        // OPTION 3
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 5) // id value is passed using the .pathParam() method - more readable & understandable
                .when().get(apiEndpoint + "/{id}" );


        // response.prettyPrint();
        // System.out.println(response.statusCode());

        // assertEquals(200, response.statusCode());
        assertEquals(HttpStatus.SC_OK, response.statusCode()); // this will do the same thing in fancy way

        // assertEquals("application/json", response.contentType());
        // assertEquals("application/json", response.getHeader("Content-Type"));
        assertEquals(ContentType.JSON.toString(), response.contentType());


        //  And "Blythe" should be in response payload(body)
        assertTrue(response.body().asString().contains("Blythe"));


    }


    /**
     *         Given accept type is Json
     *         And Id parameter value is 500
     *         When user sends GET request to /api/minions/{id}
     *          -----------------------------
     *         Then response status code should be 404
     *         And response content-type: application/json
     *         And "Not Found" message should be in response payload
     */

    @DisplayName( "GET /api/minions/{id} with invalid ID")
    @Test
    public void singleMinionNotFoundTest () {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("minion_id", 500)
                .when().get(apiEndpoint + "/{minion_id}");

        //System.out.println("Response Status Code: " + response.statusCode());
        //assertEquals(404, response.statusCode());
        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode()); //this will do the same thing

        assertEquals(ContentType.JSON.toString(), response.contentType());

        assertTrue(response.body().asString().contains("Not Found"));


    }
}
