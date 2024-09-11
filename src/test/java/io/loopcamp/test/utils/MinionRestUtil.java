package io.loopcamp.test.utils;

import com.github.javafaker.Faker;
import io.loopcamp.pojo.Minion;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class MinionRestUtil extends MinionApiTestBase{

    /**
     * This method will delete record from DB by provided valid ID
     * @param minionId
     */
    public static void deleteMinionById (int minionId) {
        given().accept(ContentType.JSON)
                .and().pathParam("new_minion_id", minionId)
                .when().delete("/{new_minion_id}")
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT); // SC_NO_CONTENT -- > 204
    }

    /**
     * This method is to generate a new Minion
     * with randomly auto generated data
     * @return new Minion
     */
    public static Minion getNewMinion () {

        Faker faker = new Faker();
        Minion newMinion = new Minion();


        newMinion.setName(faker.name().firstName());
        newMinion.setPhone(faker.numerify("##########"));
        int num = faker.number().numberBetween(1, 3); // give me 1 or 2 randomly
        if (num == 1) {
            newMinion.setGender("Female");
        } else {
            newMinion.setGender("Male");
        }

        return newMinion;
    }
    public static Minion getMinionById (int minionId) {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("minion_id", minionId)
                .when().get("/{minion_id}");

        Minion minion = response.as(Minion.class);
        return minion;
    }

}
