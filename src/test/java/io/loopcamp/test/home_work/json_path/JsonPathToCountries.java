package io.loopcamp.test.home_work.json_path;
import io.loopcamp.test.utils.HRApiTestBase;
import io.loopcamp.test.utils.ZippoApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonPathToCountries extends HRApiTestBase {
/* Q1:
- Given accept type is Json
- Path param value- US
- When users sends request to /countries
- Then status code is 200
- And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
- And Region_id is 2 */

    @DisplayName("Get/ countries/US")
    @Test
    public void getCountryUS() {
        Map<String, Object> mapQuery = new HashMap<>();
        mapQuery.put("country_id", "United States");


        Response response = given().accept(ContentType.JSON).
                and().queryParams(mapQuery)
                .when().get("/countries");
response.prettyPrint();


        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        JsonPath jsonPath = response.jsonPath();
        List<String> countryWithcountry_IdUS =  jsonPath.getList("items.findAll{region_id==10}.country_name");
        System.out.println("countryWithcountry_IdUS = " + countryWithcountry_IdUS);
        //assertEquals();

    }
}