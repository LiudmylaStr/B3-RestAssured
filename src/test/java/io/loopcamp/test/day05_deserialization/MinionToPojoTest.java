package io.loopcamp.test.day05_deserialization;
import io.loopcamp.pojo.Minion;
import io.loopcamp.test.utils.MinionApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionToPojoTest extends MinionApiTestBase {
    /**
     Given accept type is application/json
     And Path param id = 10
     When I send GET request to /minions
     --------------
     Then status code is 200
     And content type is json
     And minion data matching:
     id > 10
     name>Lorenza
     gender >Female
     phone >3312820936
     */

    @DisplayName("GET /{id}")
    @Test
    public void minionToPojoTest () {
        Response response = given().accept(ContentType.JSON).
                and().pathParam("id", 10).
                when().get("/{id}"); //  ---- > /10

        // response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());


        /*
             {
                "id": 10,
                "gender": "Female",
                "name": "Lorenza",
                "phone": "3312820936"

            }
         */

        // DESERIALIZATION: JSON --- > JAVA CUSTOM OBJECT --- > converting JSON response body into Java Custom Object (POJO / Beans)
        // IMPORTANT method name: .as();


        Minion minionObj = response.as(Minion.class);

        // System.out.println(minionObj.id);
        // minionObj.gender = "Male";

        // After making instance variables private, I can not longer call the variables directly like below. So, I used @Data to be able to call getters from encapsulation
//        assertEquals(10, minionObj.id);
//        assertEquals("Female", minionObj.gender);
//        assertEquals("Lorenza", minionObj.name);
//        assertEquals("3312820936", minionObj.phone);


        // After we applied @Data encapsulation from LOMBOK, we use getters to get the values to asser
        assertEquals(10, minionObj.getId());
        assertEquals("Female", minionObj.getGender());
        assertEquals("Lorenza", minionObj.getName());
        assertEquals("3312820936", minionObj.getPhone());

    }

}

