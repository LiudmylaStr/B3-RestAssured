package io.loopcamp.test.day07_b_put_patch;

import io.loopcamp.pojo.Minion;
import io.loopcamp.test.utils.ConfigurationReader;
import io.loopcamp.test.utils.DBUtils;
import io.loopcamp.test.utils.MinionApiTestBase;
import io.loopcamp.test.utils.MinionRestUtil;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionPatchRequestTest extends MinionApiTestBase {
    /**
     Given accept type is json
     And content type is json
     And path param id is 179
     And json body is
     {
     "phone": 5005551234
     }
     When I send PATCH request to /minions/{id}
     Then status code 204
     */


    @DisplayName("PATCH /{id}")
    @Test
    public void minionPatchTest () {

        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("phone", 1005551234);

        int minionId = 126;

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id", minionId)
                .and().body(reqBody)
                .when().patch("/{id}")
                .then().statusCode(HttpStatus.SC_NO_CONTENT);
        //.and().body(); // Since it does not return any response body, we cant do any response body assertion


        // What are the option you can actually validate that phone is updated for the Minion id 126

        // OPTION 1 - through DB - we can send query to get that data from DB and validate
        String url = ConfigurationReader.getProperty("minion.db.url");
        String username = ConfigurationReader.getProperty("minion.db.username");
        String password = ConfigurationReader.getProperty("minion.db.password");
        DBUtils.createConnection(url, username, password);
        String query = "SELECT PHONE from MINIONS WHERE MINION_ID = " + minionId;
        Map <String, Object> dbInfo = DBUtils.getRowMap(query);
        assertThat(dbInfo.get("PHONE")+"", equalTo(reqBody.get("phone")+""));



        // OPTION 2 - Through UI
        // Navigate to the page http://34.239.134.73:8000/minions
        // Store all data from UI into a Map
        // Then get the one with the ID 126
        // And do the assertion



        // OPTION 3 - Through GET api -  assertion with hamcrest matcher in a single statement
        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id", minionId)
                .when().get("/{id}")
                .then().statusCode(HttpStatus.SC_OK)
                .and().body("phone", equalTo(reqBody.get("phone")+""));


        // OPTION 4 - Through GET api - including POJO
        Minion minion = MinionRestUtil.getMinionById(minionId);
        assertThat(minion.getPhone()+"", equalTo(reqBody.get("phone")+""));




    }
}
