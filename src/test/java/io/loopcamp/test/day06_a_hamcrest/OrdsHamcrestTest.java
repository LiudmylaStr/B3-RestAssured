package io.loopcamp.test.day06_a_hamcrest;

import io.loopcamp.test.utils.HRApiTestBase;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdsHamcrestTest extends HRApiTestBase {

    /**
     * given accept type is json
     * when I send get request to /countries
     *      ---------
     * Then status code is 200
     * and content type is json
     * and count should be 25
     * and country ids should contain "AR,AU,BE,BR,CA"
     * and country names should have "Argentina,Australia,Belgium,Brazil,Canada"
     *
     * first country id is AR
     *          items[0].country_id ==> AR
     * second country id is AU
     *          items[1].country_id ==> AU
     */
    static String firstCountryId = ""; // this needs to be static so every method has the same copy shared.
    //String firstCountryName;
    @DisplayName("GET /countries")
    @Test
    @Order(1)
    public void countriesTest () {

        firstCountryId = given().accept(ContentType.JSON)
                .when().get("/countries")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .and().body("count", is(25),
                        "items.country_id", hasItems("AR", "AU", "BE", "BR", "CA"),
                        "items.country_name", hasItems("Argentina" ,"Australia", "Belgium", "Brazil", "Canada"),
                        "items[0].country_id", is(equalTo("AR")),
                        "items[1].country_id", is(equalTo("AU")))
                .and().extract().body().path("items[0].country_id"); // this will return us the VALUE assigned to that KEY ---  > AR


        //firstCountryId = response.path("items[0].country_id"); // --- ? AR
        //firstCountryName = response.path("items[0].country_name"); // --- ? Argentina

    }


    /**
     * given accept type is json
     * when I send get request to /countries/{country_id}
     * ------------------
     * Then status code is 200
     * and content type is json
     * and in body
     *      country name is Argentina
     *      region id is 20
     *      country id AR
     *      links are not null (have values )
     */

    @DisplayName("GET /countries/{country_id}")
    @Test
    @Order(2)
    public void singleCountryTest () {
        // firstCountryId;
        // firstCountryName
        // System.out.println(firstCountryId);

        given().accept(ContentType.JSON)
                .and().pathParam("first_country_id", firstCountryId)
                .when().get("/countries/{first_country_id}")
                .then().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .and().body("country_name", is("Argentina"),
                        "region_id", is(equalTo(20)),
                        "country_id", is(firstCountryId),
                        "links", is(notNullValue()));
    }}