package io.loopcamp.test.day03_path_method;
import io.loopcamp.test.utils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HRApiGetTest {

    /**
     * User should be able to see all regions when sending GET request to /ords/hr/regions
     *      Given accept type is json
     *      When user send get request to /regions
     *          -----------
     *      Status code should be 200
     *      Content type should be "application/json"
     *      And body should contain "Europe"
     */


    @BeforeEach
    public void setUp () {
        baseURI = ConfigurationReader.getProperty("hr.api.url"); // Since we did static import - we can use baseURI which come as a global variable to be used automatically as part of the endpoint
    }

    @DisplayName("GET /regions")
    @Test
    public void getRegionTest () {
        Response response = given().accept(ContentType.JSON)
                .when().get( "/regions"); // baseURI + "/regions"
         //assertEquals(200,response.statusCode()); - we can use this code
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("Europe"));

        // response.prettyPrint();
        // System.out.println(response.getHeaders());
    }


    /**
     Given accept type is json
     And Path param "region_id" value is 10
     When user send GET request to /ords/hr/regions/{region_id}
     -----------------
     Status code should be 200
     Content type should be "application/json"
     And body should contain "Europe"
     */

    @DisplayName("GET /regions/{region_id}")
    @Test
    public void getSingleRegionPathParamTest() {

        /**
         * log().all() can be used for request or response to show all the details in the console
         */


        /*
        // Example with Request log all and Response log all
        // NOTE:  since we have then().... part we can not assign this statement into Response response
        given().log().all().accept(ContentType.JSON)
                .and().pathParam("reg_id",  10)
                .when().get("/regions/{reg_id}") // baseURI + "/regions"
                .then().log().all().assertThat().statusCode(HttpStatus.SC_OK);
         */


        Response response = given().log().all().accept(ContentType.JSON)
                .and().pathParam("reg_id",  10)
                .when().get("/regions/{reg_id}"); // baseURI + "/regions"


        //response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("Europe"));

    }




}
