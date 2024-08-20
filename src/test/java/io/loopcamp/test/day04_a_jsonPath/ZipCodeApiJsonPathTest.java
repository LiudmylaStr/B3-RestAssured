package io.loopcamp.test.day04_a_jsonPath;

import io.loopcamp.test.utils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ZipCodeApiJsonPathTest {

    /**
     ***Zip code task.  http://api.zippopotam.us/us/22031
     Given Accept application/json
     And path zipcode is 22031
     When I send a GET request to /us endpoint
     --------------------
     Then status code must be 200
     And content type must be application/json
     And Server header is cloudflare
     And Report-To header exists
     And body should contains following information
     post code is 22031
     country  is United States
     country abbreviation is US
     place name is Fairfax
     state is Virginia
     latitude is 38.8604
     */

    @BeforeEach
    public void setUp (){
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }


    @DisplayName("GET /us/zipcode")
    @Test
    public void zipcodeResponseWithJsonPathMethodTest () {

        //Response response = given().log().all().accept(ContentType.JSON).
        Response response = given().accept(ContentType.JSON).
                and().pathParam("country", "US")
                .and().pathParam("zipcode", "22031")
                .when().get("/{country}/{zipcode}");


        // response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        // System.out.println("All headers: \n" + response.getHeaders());
        assertEquals("cloudflare", response.getHeader("Server"));
        assertNotNull(response.getHeader("Report-To"));


        // NOTE: this is how we read the response body/ response payload with .path() method
        // System.out.println("" + response.path("'post code'"));
        // System.out.println(response.path("'post code'")); // Since it returns <T> generic, cant print directly.
        assertEquals("22031", response.path("'post code'"));


        // assigning the response jason payload/body to JsonPath
        JsonPath jsonPath = response.jsonPath();

        // JsonPath has prebuilt-in methods which helps also validate the data types
        // System.out.println(jsonPath.getString("'post code'"));
        assertEquals("22031", jsonPath.getString("'post code'"));

        // calling our reusable method for validation fo Zipcode
        verifyZipCode("22031", jsonPath);

        assertEquals("United States", jsonPath.getString("country"));
        assertEquals("US", jsonPath.getString("'country abbreviation'"));
        assertEquals("Fairfax", jsonPath.getString("places[0].'place name'"));
        assertEquals("Virginia", jsonPath.getString("places[0].state"));
        assertEquals("38.8604", jsonPath.getString("places[0].latitude"));


    }


    // we made one reusable method to validate the zipcode
    public void verifyZipCode (String expZipcode, JsonPath jsonPath){
        assertEquals(expZipcode, jsonPath.getString("'post code'"));
    }

}
