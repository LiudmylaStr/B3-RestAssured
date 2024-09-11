package io.loopcamp.test.day05_deserialization;

import io.loopcamp.pojo.Minion;
import io.loopcamp.test.utils.MinionApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class AllMinionsToPojoTest extends MinionApiTestBase {

    /**
     Given accept type is json
     when I send GET request to /minions
     ----
     Then status code is 200
     And content type is json
     And I can convert json to list of minion pojos
     */

    @DisplayName("GET /minions")
    @Test
    public void allMinionsToPojoTest () {

        Response response = given().accept(ContentType.JSON).
                when().get();

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        // And I can convert json to list of minion pojos


        // The code below will give MismatchedInputException because the response body is not nor directly JSON object, it is JSON array object - list
        // Minion minionObj = response.as(Minion.class);

        // I converted JSON Response Body/Payload which is JSON Array - to --> List of Minion POJO classes
        JsonPath jsonPath = response.jsonPath();
        List<Minion> allMinions = jsonPath.getList("", Minion.class);  // "" --> tells that hey start from the beginning in JSON response body


        for (Minion eachMinion : allMinions) {
            System.out.println( eachMinion.getId() );
        }

        List <Minion> allFemaleMinions = new ArrayList<>();

        for (Minion eachMinion : allMinions) {
            if (eachMinion.getGender().equals("Female")){
                allFemaleMinions.add(eachMinion);
            }
        }


        System.out.println(allFemaleMinions);
        System.out.println("Num of Female Minions: " + allFemaleMinions.size());

    }
}