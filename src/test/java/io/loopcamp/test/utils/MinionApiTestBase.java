package io.loopcamp.test.utils;

import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.baseURI;

public class MinionApiTestBase {

    @BeforeAll
    public static void setUp () {
        baseURI = ConfigurationReader.getProperty("minion.api.url"); // Since we did static import - we can use baseURI which come as a global variable to be used automatically as part of the endpoint
    }

}