package io.loopcamp.test.day06_b_post_put_delete;

import io.loopcamp.pojo.Minion;
import io.loopcamp.test.utils.MinionRestUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionPostThenGetTest extends MinionRestUtil {


    @DisplayName("POST and GET /{id}")
    @Test
    public void postNewMinionThenGetTest () {

        Minion minionReqPayload = MinionRestUtil.getNewMinion();


        // THIS is all about POST request
        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(minionReqPayload) // SERIALIZATION --- > from Custom Java object (Pojo) to JSON
                .when().post();  // POST request

        assertThat(response.statusCode(), is(HttpStatus.SC_CREATED));
        int id = response.path("data.id");
        System.out.println(id);



        // THIS is all about GET request
        Response response1 = given().accept(ContentType.JSON)
                .and().pathParam("new_minion_id", id)
                .when().get("/{new_minion_id}"); // GET request
        response1.prettyPrint();


        // DESERIALIZATION
        Minion minionFromGetReq = response1.as(Minion.class);


        // Assert by two Minion Objects
        assertThat(minionFromGetReq.getGender(), equalTo(minionReqPayload.getGender()));
        assertThat(minionFromGetReq.getName(), equalTo(minionReqPayload.getName()));
        assertThat(minionFromGetReq.getPhone(), equalTo(minionReqPayload.getPhone()));


        // THIS is all about DELETE request
        MinionRestUtil.deleteMinionById(id);

    }
}

