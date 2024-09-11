package io.loopcamp.test.day06_a_hamcrest;

import io.loopcamp.test.utils.MinionApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionHamcrestTest extends MinionApiTestBase {
    int actualId;
    /**
     given accept type is json
     and path id is 24
     When I send get request to /minions/{id}
     ----------
     then status code is 200
     and content type is application/json
     and validate response body
     and id" is 24,
     "name" is "Julio",
     "gender" is "Male",
     "phone" is "9393139934"
     */

    @DisplayName("GET /{id}")
    @Test
    public void singleMinionTest () {

        /*
            // This is using JUnit to do the assertions
            Response response = given().accept(ContentType.JSON)
                    .and().pathParam("id", 24)
                    .when().get("/{id}");

            assertEquals(HttpStatus.SC_OK, response.statusCode());
            assertEquals(ContentType.JSON.toString(), response.contentType());

            assertEquals((Integer) 24, response.path("id"));
            assertEquals("Julio", response.path("name"));
            assertEquals("Male", response.path("gender"));
            assertEquals("9393139934", response.path("phone"));


            int actualId = response.path("id");
         */


        given().accept(ContentType.JSON)
                .and().pathParam("id", 24)
                .when().get("/{id}")
                .then().statusCode(HttpStatus.SC_OK) // assertion for Response status code
                .and().contentType(ContentType.JSON) // assertion for Response Content Type
                .assertThat().body("id", is(24), // JUnite assertion -- >   assertEquals((Integer) 24, response.path("id"));
                        "name", equalTo("Julio"),
                        "gender", is(equalTo("Male")),
                        "phone", is("9393139934"));
    }



    /**
     * Given accept type is json
     * And query param nameContains value is "e"
     * And query param gender value is "Female"
     * When I send get request to /minions/search
     * ----------
     * Then status code is 200
     * And content type is Json
     * And header Date contains 2024
     * And json response data is as expected
     * totalElement is 33
     * has ids: 94, 98, 91, 81
     * has names: Jocelin, Georgianne, Catie, Marylee,Elita
     * every gender is Female
     * every name contains e
     */
    @DisplayName("GET /search? -- query params")
    @Test
    public void searchMinionTest () {

        // given().log().all().accept(ContentType.JSON) // if you want to see the logs fo Request, you can use this line
        given().accept(ContentType.JSON)
                .and().queryParam("nameContains", "e")
                .and().queryParam("gender", "Female")
                .when().get("/search")
                //.then().log().all().statusCode(HttpStatus.SC_OK); // since we do it in one statement, we can have response logs as well if needed
                .then().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                //.and().header("Date", containsString("2024"))  // Sat, 24 Aug 2024 15:18:53 GMT
                .and().header("Date", containsString(LocalDate.now().getYear() + "")) //To make it dynamic, we used the LocalDate class from time package in Java
                // .and().assertThat().body(......) // you can do this or the one below since everything after then() is assertion, assertThat() is not required
                .and().body("totalElement", is(equalTo(33)),
                        "content.id", hasItems(94, 98, 91, 81),
                        "content.name", hasItems("Jocelin", "Georgianne", "Catie", "Marylee", "Elita"),
                        "content.gender", everyItem(is(equalTo("Female"))),
                        "content.name", everyItem(containsStringIgnoringCase("e")))
                .log().all(); // Response related log


    }





}
