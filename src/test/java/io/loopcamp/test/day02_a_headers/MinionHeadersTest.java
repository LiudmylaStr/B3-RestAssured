package io.loopcamp.test.day02_a_headers;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionHeadersTest {
    /**
     • When I send a GET request to --- > minions_base_url/api/minions -- > your_ip:8000 = minions_base_url
     -------------
     • Then Response STATUS CODE must be 200
     • And I Should see all Minions data in XML format
     */


    String requestUrl = "http://3.89.221.22:8000/api/minions";
    @DisplayName("GET /api/minions and expect XML response format as default")
    @Test
    public void getAllMinionsHeaderTest (){
        when().get(requestUrl)
                .then().assertThat().statusCode(200)
                //.and().contentType("application/xml");
                .and().contentType(ContentType.XML);  // This is ENUM which CONSTANTS
    }


    /**
     * Given Accept type is application/xml
     • When I send a GET request to
     • minions_base_url/api/minions
     -----------------------------
     • Then Response STATUS CODE must be 200
     • And I Should see all Minions data in xml format
     */

    @DisplayName("GET /api/minions and expect XML response format with asking for XML")
    @Test
    public void getAllMinionsHeadersInXMLTest(){

        given().accept(ContentType.XML)
                .when().get(requestUrl)
                .then().assertThat().statusCode(200)
                //.and().contentType("application/xml");
                .and().contentType(ContentType.XML);  // This is ENUM which CONSTANTS

    }


    /**
     * Given Accept type is application/json
     • When I send a GET request to
     • minions_base_url/api/minions
     -----------------------------
     • Then Response STATUS CODE must be 200
     • And I Should see all Minions data in json format
     */

    @DisplayName("GET /api/minions and expect JSON response format with asking for JSON")
    @Test
    public void getAllMinionsHeadersInJSONTest(){

        given().accept(ContentType.JSON)
                .when().get(requestUrl)
                .then().assertThat().statusCode(200)
                //.and().contentType("application/json");
                .and().contentType(ContentType.JSON);  // This is ENUM which CONSTANTS

    }


    /**
     * Given Accept type is application/xml
     • When I send a GET request to  --- >minion_base_url/api/minions
     -----------------------------
     • Then Response STATUS CODE must be 200
     • And read headers //(how to read a specific header)
     */

    @DisplayName("GET /api/minions with requested header JSON -- reader header options ")
    @Test
    public void readResponseHeadersTest() {
        Response response = given().accept(ContentType.JSON)

                .when().get(requestUrl);
        //.then().assertThat().statusCode(200);  // Since after then(), it does assertions meaning it does nto return Response, we cannot assign it to Response response

        // assert status code
        assertEquals(200, response.statusCode());
        // response.prettyPrint();

        // assert content type
        assertEquals(ContentType.JSON.toString(), response.contentType()); // to be able to assert, the data type needs to match as well. So, we used toString() method to compare String to String


        // How to read each header from response  ---- > getHeader(key)
        System.out.println("Header - Date: " + response.getHeader("Date"));
        System.out.println("Header - Content-Type: " + response.getHeader("Content-Type"));
        System.out.println("Header - Connection: " + response.getHeader("Connection"));


        System.out.println();
        // How to read ALL HEADER from response. ----- > getHeaders()
        System.out.println("All Headers: \n" + response.getHeaders());


        // How can you validate if the specific header is not no empty
        assertTrue(response.getHeader("Keep-Alive") != null);
        assertNotNull(response.getHeader("Keep-Alive")); // this will do the same thing as above

    }



}
