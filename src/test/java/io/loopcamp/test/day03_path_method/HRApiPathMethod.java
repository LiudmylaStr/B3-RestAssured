package io.loopcamp.test.day03_path_method;

import io.loopcamp.test.utils.HRApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HRApiPathMethod extends HRApiTestBase {
    // NOTE: Create HRApiTestBase class
    // and store the baseURI in the class
    // and in here extend that class

    /**
     * Given accept is json
     * When I send GET request to /countries
     * ----------
     * And status code is 200
     * And first country_id value is AR
     * And first country_name value is Argentina
     */
    @DisplayName("GET /{country}")
    @Test
    public void readHRResponseBodyPathMethodTest() {
        Response response = given().accept(ContentType.JSON).
                when().get("/countries");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        System.out.println("1st Country ID: " + response.path("items[0].country_id"));
        System.out.println("1st Country Name: " + response.path("items[0].country_name"));


        assertEquals("AR", response.path("items[0].country_id"));
        // You can assign it into a variable and use the variable in you assertions
        // String actual1stCount_ID = response.path("items[0].country_id");
        //assertEquals("AR", actual1stCount_ID);

        assertEquals("Argentina", response.path("items[0].country_name"));

        List <String> allCountryNames = response.path("items.country_name");
        System.out.println("All Country Names: " + allCountryNames);
        System.out.println("Num of Countries: " + allCountryNames.size());

    }}