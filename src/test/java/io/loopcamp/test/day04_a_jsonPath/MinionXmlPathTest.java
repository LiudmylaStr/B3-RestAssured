package io.loopcamp.test.day04_a_jsonPath;

import io.loopcamp.test.utils.MinionApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class MinionXmlPathTest extends MinionApiTestBase {
    /**
     Given accept type is application/xml
     When I send GET request to baseURI
     -------------
     Then status code is 200
     And content type is XML
     And minion at index 0 is matching: (NOTE: Your index 0 might be different)
     id =>       1
     name =>     Meade
     gender =>   Male
     phone =>    9994128235
     */

    @DisplayName("GET /minions")
    @Test
    public void minionXmlPathTest () {

        Response response = given().accept(ContentType.XML).
                when().get();

        // response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.XML.toString(), response.contentType());


        // assign/convert response into XmlPath
        XmlPath xmlPath = response.xmlPath();
        System.out.println("1st ID: " + xmlPath.getInt( "List.item[0].id"));
        System.out.println("1st Gender: " + xmlPath.getString("List.item[0].gender"));
        System.out.println("1st Name: " + xmlPath.getString( "List.item[0].name"));
        System.out.println("1st Phone No: " +  xmlPath.getString("List.item[0].phone"));

        assertEquals(1, xmlPath.getInt( "List.item[0].id"));
        assertEquals("Male", xmlPath.getString("List.item[0].gender"));
        assertEquals("Meade", xmlPath.getString( "List.item[0].name"));
        assertEquals("9994128233", xmlPath.getString("List.item[0].phone"));


        // how to get all names
        List<String> allNames = xmlPath.getList("List.item.name");
        System.out.println("All names: " + allNames);

    }
}