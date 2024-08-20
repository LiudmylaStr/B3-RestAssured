package io.loopcamp.test.day04_a_jsonPath;

import io.loopcamp.test.utils.HRApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HREmployeesJsonPathTest extends HRApiTestBase {



    /**
     Given accept type is Json
     And query param limit is 2
     when I send GET request to /employees
     Then I can use jsonPath to query and read values from json body
     */


    @DisplayName("GET /employees?limit=2")
    @Test
    public void jsonPathEmployeeTEst () {
        Map <String, Object> mapQuery = new HashMap<>();
        mapQuery.put("limit", 20);


        Response response = given().accept(ContentType.JSON).
                and().queryParams(mapQuery)
                .when().get("/employees");

        //response.prettyPrint();
        // validate the 1st person's name is Steven on this query param: limit=2
        assertEquals("Steven", response.path("items[0].first_name"));

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());


        JsonPath jsonPath = response.jsonPath();
        assertEquals("Steven", jsonPath.getString("items[0].first_name"));

        List <String> emails =  jsonPath.getList("items.email");
        System.out.println("Emails: " + emails);


        // assert that "NYANG" is among the emails from response
        assertTrue(emails.contains("NYANG"));

        // get all employees first name who work for department_id = 90
        /*
            SELECT first_name from employees
            WHERE department_id = 90;
         */
        // findAll() --- > it comes from scripting groovy language (based on Java)
        List <String> firstNames = jsonPath.getList("items.first_name");
        System.out.println("First names:" + firstNames);
        System.out.println("Num of first names: "  + firstNames.size());


        // for those who are under department_id 90
        List <String> firstNamesWithDepId90 =  jsonPath.getList("items.findAll{it.department_id==90}.first_name");
        System.out.println("First names with dep id - 90: " + firstNamesWithDepId90);


        // find all first names for whose who works as "IT_PROG",
        List <String> firstNamesWithJobIdIT_PROG= jsonPath.getList("items.findAll{it.job_id=='IT_PROG'}.first_name");
        System.out.println("First name with job id - IT PROG: " + firstNamesWithJobIdIT_PROG);


        // find all first names for those who makes more than 5000
        List <String> firstnameWithSalaryHigherThan5000 = jsonPath.getList("items.findAll{it.salary > 5000}.first_name");
        System.out.println("First name with Salary higher than 5000: " + firstnameWithSalaryHigherThan5000);
        System.out.println("Num of people making more than $5000: " + firstnameWithSalaryHigherThan5000.size());


        // find all first names for those who makes max salary
        System.out.println( jsonPath.getList("items.salary"));
        String maxName = jsonPath.getString("items.max{it.salary}.first_name");
        System.out.println("Max name: " + maxName);

        // TODO: search for groovy language that returns a list with the dynamic max value
        // System.out.println("--------");
        // List <String> maxNames= jsonPath.getList("items.findAll{it.salary == items.min{it.salary}.salary}.first_name");
        //  System.out.println("Max names: " + maxNames);



        // find all first names for those who makes mix salary
        System.out.println( jsonPath.getList("items.salary"));
        String minName = jsonPath.getString("items.min{it.salary}.first_name");
        System.out.println("Min name: " + minName);


        // get me the minimum salary from response body
        String minSalary = jsonPath.getString("items.min{it.salary}.salary");
        System.out.println(minSalary);




    }

}
