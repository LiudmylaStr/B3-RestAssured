package io.loopcamp.test.day04_a_jsonPath;

import io.loopcamp.test.utils.MinionApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionJsonPathApiTest extends MinionApiTestBase {


/**
 Given accept is json
 And path param id is 13
 When I send get request to /minions/{id}
 ----------
 Then status code is 200
 And content type is json
 And id value is 13
 And name is "Jaimie"
 And gender is "Female"
 And phone is "7842554879"
 */

@DisplayName("GET /{id}")
@Test
public void getMinionResBodyJasonPathMethodTest() {

//        // Here we directly assigned it into JsonPath
//        // NOTE: If you do it directly, there is not way to get the status and validate
//         JsonPath jsonPath = given().accept(ContentType.JSON).
//                and().pathParam("id", 13).
//                when().get("/{id}").jsonPath();


    Response response = given().accept(ContentType.JSON).
            and().pathParam("id", 13).
            when().get("/{id}");


    // Assertion for status code
    assertEquals(HttpStatus.SC_OK, response.statusCode());
    // Assertion for Content Type
    assertEquals(ContentType.JSON.toString(), response.contentType());

    // assigning the response into JsonPath
    JsonPath jsonPath = response.jsonPath();


        /*
            {
                "id": 13,
                "gender": "Female",
                "name": "Jaimie",
                "phone": "7842554879"
            }
         */
    assertEquals(13, jsonPath.getInt("id"));
    assertEquals("Female", jsonPath.getString("gender"));
    // assertEquals("Female", jsonPath.getInt("gender")); // This will give an error since "Female" is not parsable into Int, so, it has to be String
    assertEquals("Jaimie", jsonPath.getString("name"));
    //assertEquals(7842554879L, jsonPath.getLong("phone"));
    assertEquals("7842554879", jsonPath.getString("phone"));


}
}