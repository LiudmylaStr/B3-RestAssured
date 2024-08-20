package io.loopcamp.test.day03_path_method;
import io.loopcamp.test.utils.MinionApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionPathMethodApiTest extends MinionApiTestBase {

    /**
     * Given accept is json
     * And path param id is 13
     * When I send get request to /minions/{id}
     * ----------
     * Then status code is 200
     * And content type is json
     * And id value is 13
     * And name is "Jaimie"
     * And gender is "Female"
     * And phone is "7842554879"
     */


    /*
        {
            "id": 13,
            "gender": "Female",
            "name": "Jaimie",
            "phone": "7842554879"
        }

        response.body().asString().contains("Jaimie")
            - it the WHOLE RESPONSE BODY includes it in anywhere in body

        to be able to specifically check for the VALUE which belongs to the specific KEY, we have multiple ways to do it
     */


    @DisplayName("GET /{id}")
    @Test
    public void readMinionResponseBodyPathMethodTest () {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 13)
                .when().get("/{id}");  // baseURI + "/{id}}" --- http://34.239.134.73:8000/api/minions/13//should be my url

        /*
            {
                "id": 13,
                "gender": "Female",
                "name": "Jaimie",
                "phone": "7842554879"
            }
         */

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals(ContentType.JSON.toString(), response.getContentType());

        //assertTrue(response.getBody().asString().contains("13"));
        // response.path("id") --- > this will return the VALUE assigned to KEY called id --- from response body
        System.out.println("id: " + response.path("id"));
        System.out.println("gender: " + response.path("gender"));
        System.out.println("name: " + response.path("name"));
        System.out.println("phone: " + response.path("phone"));


        assertEquals((Integer)13, response.path("id"));
        assertEquals("Female", response.path("gender"));
        assertEquals("Jaimie", response.path("name"));
        assertEquals("7842554879", response.path("phone"));

    }


    /**
     Given accept is json
     When I send get request to /minions
     -------------
     Then status code is 200
     And content type is json
     And I can navigate json array object
     */


    @DisplayName("GET all minions")
    @Test
    public void readMinionResponseJSONBodyWithArrayPathMethodTest () {

        Response response = given().accept(ContentType.JSON)
                .when().get();

        //response.prettyPrint();
        /*
            [
                {
                    "id": 1,
                    "gender": "Male",
                    "name": "Meade",
                    "phone": "9994128234"
                },
                {
                    "id": 2,
                    "gender": "Male",
                    "name": "Nels",
                    "phone": "4218971348"
                }
             ]
         */



        // since the response return array of json objects - I can get all the objects KEY VALUE as all array elements
        System.out.println("All ids: " + response.path("id") );
        System.out.println("All names: " + response.path("name"));

        // print the 1st id and name
        System.out.println("1st id: " + response.path("id[0]"));
        System.out.println("1st id: " + response.path("[0].id"));

        System.out.println("1st name: " + response.path("name[0]"));
        System.out.println("1st name: " + response.path("[0].name"));


        // Print the last minion id and name
        System.out.println("Last Id: " + response.path("id[-1]"));
        //System.out.println("Last Id: " + response.path("[-1].id")); // this will not find it
        System.out.println("Last Name: " + response.path("name[-1]"));


        // Can you get all the names and store into one Java collection
        List <String> allNames = response.path("name");
        System.out.println("Total minion name count: " + allNames.size());

        // Can you print each name from response body in this format --- > Hi $name!
        for (String eachName : allNames) {
            System.out.println("Hi " + eachName + "!");
        }
        System.out.println("-------------------");
        allNames.forEach(each -> System.out.println("Hi " + each + "!"));



    }
}

