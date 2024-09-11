package io.loopcamp.test.day07_b_put_patch;

import io.loopcamp.test.utils.MinionApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionPutRequestTest extends MinionApiTestBase {

    /**
     Given accept type is json
     And content type is json
     And path param id is 180
     And json body is
     {
     "gender": "Male",
     "name": "PutRequest",
     "phone": 1234567425
     }
     When I send PUT request to /minions/{id}
     Then status code 204
     */

    @DisplayName("PUT /{id}")
    @Test
    public void updateMinionTest () {

        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("gender", "Female");
        reqBody.put("name", "PutRequestt");
        reqBody.put("phone", 1234567425);

        Response response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and().pathParam("id", 126)
                .and().body(reqBody)
                .when().put("/{id}");


        response.prettyPrint(); //Since the response body does not return anything back, it will be empty

        System.out.println("Status Code: " + response.statusCode());
        assertThat(response.statusCode(), is(equalTo(HttpStatus.SC_NO_CONTENT)));


        /**
         * PUT --Http Method
         *
         *  We need to provide whole body to update
         *  Some company use PUT request as POST method as well (Not all the companies)
         *      Logic, if the given id exist, just update
         *      If it does not exist, then create a new one.
         */
    }
}
