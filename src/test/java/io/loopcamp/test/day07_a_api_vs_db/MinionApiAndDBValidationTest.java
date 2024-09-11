package io.loopcamp.test.day07_a_api_vs_db;
import io.loopcamp.pojo.Minion;
import io.loopcamp.test.utils.ConfigurationReader;
import io.loopcamp.test.utils.DBUtils;
import io.loopcamp.test.utils.MinionApiTestBase;
import io.loopcamp.test.utils.MinionRestUtil;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
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

public class MinionApiAndDBValidationTest extends MinionApiTestBase {

    /**
     given accept is json
     and content type is json
     and body is:
     {
     "gender": "Male",
     "name": "PostVSDatabase",
     "phone": 1234567425
     }
     When I send POST request to /minions
     Then status code is 201
     And content type is "application/json;charset=UTF-8"
     And "success" is "A Minion is Born!"
     When I send database query
     SELECT name, gender, phone
     FROM minions
     WHERE minion_id = newIdFrom Post request;
     Then name, gender, phone values must match with POST request details
     */

    @DisplayName("POST /minions and validate in DB")
    @Test
    public void postMinionThenValidateInDbTest () {

//    OPTION 1 - with string
//        String reqBody = "{\n" +
//                "                 \"gender\": \"Male\",\n" +
//                "                 \"name\": \"PostVSDatabase\",\n" +
//                "                 \"phone\": 1234567425\n" +
//                "             }";

//    OPTION 2 - with POJO class (+ Utility clas with a re-useful method which generates random data)
//        Minion reqBody = MinionRestUtil.getNewMinion();


//    OPTION 3 - with Java Collection class
        Map <String, Object> reqBody = new HashMap<>();
        reqBody.put("gender", "Male");
        reqBody.put("name", "PostVSDatabase");
        reqBody.put("phone", 1234567425);

        Response response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and().body(reqBody) // SERIALIZATION -- > from JAVA to JSON (MAP - JSON)
                .when().post();

        response.prettyPrint();
        assertThat(response.statusCode(), is(equalTo(HttpStatus.SC_CREATED))); // Asserted Response Status Code
        assertThat(response.contentType(), equalTo("application/json;charset=UTF-8")); // Asserted Response Header


        // Validate Response BODY
        // Option 1 - contains()

        // Option 2 - path()
        // assertThat(response.path("success"), equalTo("A Minion is Born!"));

        // Option 3 - jsonPath()
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), equalTo("A Minion is Born!"));


        // Option 4 - with DESERIALIZATION -- > .as(.....class)
        // Minion minion1 = response.as(Minion.class);    // JSON to Java POJO class
        // Map <String, Object> minion2 = response.as(Map.class); // JSON to Java Collection Class


        int newMinionID = jsonPath.getInt("data.id");
        System.out.println(newMinionID);

        // Whatever I created with POST API, I can retrieve it with the UNIQUE identifier assigned to that which is  MINION_ID
        // SELECT * FROM MINIONS WHERE MINION_ID = 121;
        String query = "SELECT name, gender, phone FROM MINIONS WHERE MINION_ID = " + newMinionID;


        //String dbUrl = "jdbc:oracle:thin:@34.239.134.73:1521:XE";
        String dbUrl = ConfigurationReader.getProperty("minion.db.url");
        String dbUsername = ConfigurationReader.getProperty("minion.db.username");
        String dbPassword = ConfigurationReader.getProperty("minion.db.password");

        // create connection to Minion DB for automation testing
        DBUtils.createConnection(dbUrl, dbUsername, dbPassword);
        Map <String, Object> dbResult = DBUtils.getRowMap(query);
        System.out.println("Info From DB:" + dbResult);


        assertThat(dbResult.get("GENDER"), equalTo(reqBody.get("gender")));
        assertThat(dbResult.get("NAME"), equalTo(reqBody.get("name")));
        assertThat(dbResult.get("PHONE")+"", equalTo(reqBody.get("phone")+""));


        MinionRestUtil.deleteMinionById(newMinionID);
        DBUtils.destroy();


    }

}





