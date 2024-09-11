package io.loopcamp.test.day06_b_post_put_delete;

import io.loopcamp.pojo.Minion;
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

public class MinionPostTest extends MinionApiTestBase {


    /**
     given accept is json
     and content type is json
     and body is:
     {
     "gender": "Male",
     "name": "TestPost4",
     "phone": 1234567425
     }
     When I send POST request to /minions
     ---------------
     Then status code is 201
     And content type is "application/json;charset=UTF-8"
     And "success" is "A Minion is Born!"
     Data name, gender , phone matches my request details
     */

    @DisplayName("POST and DELETE /{id} as String")
    @Test
    public void addNewMinionTest () {

        // TODO: Practice on your own using Java Faker library to auto populate name, gender, phone
        String requestJSONBody = "{\n" +  // always do with the double quote, so it auto puts everything correctly.
                "  \"gender\": \"Male\",\n" +
                "  \"name\": \"Loopcamp\",\n" +
                "  \"phone\": 1234567890\n" +
                "}";

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body( requestJSONBody )  // SERIALIZATION --- >  Converting from JAVA (String Object) -- > JSON
                .when().post();

        // verification status
        assertEquals(HttpStatus.SC_CREATED, response.statusCode()); // JUNit assertion
        assertThat(response.statusCode(), is (HttpStatus.SC_CREATED)); // Hamcrest assertion

        // with jasonPath - for body verification
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), equalTo("A Minion is Born!"));

        // assertThat(jsonPath.getInt("data.id"), is(102));
        assertThat(jsonPath.getString("data.gender"), is(equalTo("Male")));
        assertThat(jsonPath.getString("data.name"), is(equalTo("Loopcamp")));
        assertThat(jsonPath.getString("data.phone"), is(equalTo("1234567890")));


        /*
            {
                "success": "A Minion is Born!",
                "data": {
                    "id": 101,
                    "gender": "Male",
                    "name": "Loopcamp",
                    "phone": "1234567890"
                }
            }
         */
        // DELETE http://34.239.134.73:8000/api/minions/103

        int id = jsonPath.getInt("data.id");  // new id that was generated
        MinionRestUtil.deleteMinionById(id);

//      We made one reusable method and used that
//        given().accept(ContentType.JSON)
//                .and().pathParam("new_minion_id", id)
//                .when().delete("/{new_minion_id}")
//                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT); // SC_NO_CONTENT -- > 204

    }

    @DisplayName("POST and DELETE /{id} as map")
    @Test
    public void addNewMinionAsMapTest (){

        Map <String, Object> minionReqPayload = new HashMap<>();
        minionReqPayload.put("gender", "Female");
        minionReqPayload.put("name", "LoopAcademy");
        minionReqPayload.put("phone", 9876543210L);



        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body( minionReqPayload )  // SERIALIZATION --- >  Converting from JAVA (Map Object) -- > JSON
                .when().post();

        // verification status
        assertEquals(HttpStatus.SC_CREATED, response.statusCode()); // JUNit assertion
        assertThat(response.statusCode(), is (HttpStatus.SC_CREATED)); // Hamcrest assertion

        // with jasonPath - for body verification
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), equalTo("A Minion is Born!"));

        // assertThat(jsonPath.getInt("data.id"), is(102));
        assertThat(jsonPath.getString("data.gender"), is(equalTo("Female")));
        assertThat(jsonPath.getString("data.name"), is(equalTo("LoopAcademy")));
        assertThat(jsonPath.getString("data.phone"), is(equalTo("9876543210")));


        int id = jsonPath.getInt("data.id");  // new id that was generated
        System.out.println(id);
        MinionRestUtil.deleteMinionById(id);


    }

    @DisplayName("POST and DELETE /{id} as POJO")
    @Test
    public void addNewMinionAsPojoTest () {

        Minion newMinion = new Minion();
        newMinion.setGender("Male");
        newMinion.setName("LC");
        newMinion.setPhone("1234567890");


        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body( newMinion )  // SERIALIZATION --- >  Converting from JAVA (POJO Object) -- > JSON
                .when().post();

        // verification status
        assertEquals(HttpStatus.SC_CREATED, response.statusCode()); // JUNit assertion
        assertThat(response.statusCode(), is (HttpStatus.SC_CREATED)); // Hamcrest assertion

        // with jasonPath - for body verification
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), equalTo("A Minion is Born!"));

        // assertThat(jsonPath.getInt("data.id"), is(102));
        assertThat(jsonPath.getString("data.gender"), is(equalTo("Male")));
        assertThat(jsonPath.getString("data.name"), is(equalTo("LC")));
        assertThat(jsonPath.getString("data.phone"), is(equalTo("1234567890")));


        int id = jsonPath.getInt("data.id");  // new id that was generated
        System.out.println(id);
        MinionRestUtil.deleteMinionById(id);

    }

}
