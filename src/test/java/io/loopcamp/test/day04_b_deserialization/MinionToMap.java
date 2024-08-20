package io.loopcamp.test.day04_b_deserialization;
import io.loopcamp.test.utils.MinionApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionToMap extends MinionApiTestBase {


    /**
     Given accept type is application/json
     And Path param id = 10
     When I send GET request to /minions
     --------
     Then status code is 200
     And content type is json
     And minion data matching:
     id > 10
     name > Lorenza
     gender > Female
     phone > 3312820936
     */

    @DisplayName("GET /{id}")
    @Test
    public void minionToMapTest (){
        Map<String, Object> expectedMinionMap = new HashMap<>();
        expectedMinionMap.put("id", 10);
        expectedMinionMap.put("name", "Lorenza");
        expectedMinionMap.put("gender", "Female");
        expectedMinionMap.put("phone", "3312820936");


        Response response = given().accept(ContentType.JSON).
                and().pathParam("id", 10).
                when().get("/{id}");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        //response.prettyPrint();
        /*
            {
                "id": 10,
                "gender": "Female",
                "name": "Lorenza",
                "phone": "3312820936"
            }
         */

        // DESERIALIZATION: JSON --- > JAVA OBJECT --- > converting JSON response body into Java Collection Object
        // IMPORTANT method name: .as();
        // Map <String, Object> actualMinionMap = response.as(Map.class);
        Map <String, Object> actualMinionMap = response.body().as(Map.class); // same as above just more readable/understandable.
        System.out.println("Minion: " + actualMinionMap);
        System.out.println("All keys in Map: " + actualMinionMap.keySet());
        System.out.println("All values in Map: " + actualMinionMap.values());

        assertEquals(10, actualMinionMap.get("id"));
        assertEquals("Lorenza", actualMinionMap.get("name"));
        assertEquals("Female", actualMinionMap.get("gender"));
        assertEquals("3312820936", actualMinionMap.get("phone"));


        System.out.println();
        System.out.println("Actual Minion from API response body in Map: " + actualMinionMap);
        System.out.println("Expected Minion from Task in Map: " + expectedMinionMap);

        assertEquals(expectedMinionMap, actualMinionMap);


    }


}


