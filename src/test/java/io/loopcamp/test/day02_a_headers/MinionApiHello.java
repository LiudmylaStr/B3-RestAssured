package io.loopcamp.test.day02_a_headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.*;

public class MinionApiHello {
    /**
     When I send GET request to http://your_ip:8000/api/hello
     ---------------------
     Then status code should be 200
     And response body should be equal to "Hello from Minion"
     And content type is "text/plain;charset=ISO-8859-1"
     */

    String endpoint = "http://3.89.221.22:8000/api/hello";
    //String endpoint = "http://34.239.134.73:8000/api/minions/search";
    @DisplayName("GET /api/hello")
    @Test
    public void helloApiTest (){

        Response response = when().get(endpoint);

        // response status code assertions
        assertEquals(200, response.getStatusCode());
        response.then().assertThat().statusCode(200);

        // response body assertions
        assertTrue(response.body().asString().contains("Hello from Minion"));
        assertEquals("Hello from Minion", response.body().asString());
        // we will learn later how to assert directly with BDD approach as well

        // response header assertion
        assertEquals("text/plain;charset=ISO-8859-1", response.contentType());

    }



    @DisplayName("GET /api/hello -- BDD")
    @Test
    public void helloApiBDDTest (){

        // we did BDD approach of API testing using given(), when(), then() -- keywords
        // we did not do the body part - we need to learn few more things to do it.
        when().get(endpoint)
                .then().assertThat().statusCode(200)
                .and().contentType("text/plain;charset=ISO-8859-1");

    }


}
