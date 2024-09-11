package io.loopcamp.test.day06_a_hamcrest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

//import static io.restassured.RestAssured.*;
//import static org.junit.jupiter.api.Assertions.*;

public class HamcrestMatchersIntro {

    @Test
    public void numbersTest () {

        // assertEquals --- > coming from JUnit
        // assertEquals(200, 200);

        // Hamcrest assertion is another option for assertion
        assertThat(200, is(200));
        assertThat(1+3, is(4));
        assertThat(2+2, equalTo(4));
        assertThat(10+5, is(equalTo(15)));

        assertThat(100+4, greaterThan(100));
        assertThat(100+4, is(greaterThan(100)));

        // assertTrue --- > coming from JUnit
        // assertTrue(104 > 100);

        int expNum = 10;
        assertThat(expNum, is(not(5)));
        assertThat(expNum, is(not(equalTo(5))));

    }

    @Test
    public void stringTest() {
        String word = "loopcamp";

        assertThat(word, is("loopcamp"));
        assertThat(word, equalTo("loopcamp"));
        assertThat(word, is(equalTo("loopcamp")));


        // startsWith
        assertThat(word, startsWith("loop"));
        assertThat(word, startsWithIgnoringCase("LOOP"));


        // contains
        assertThat(word, containsString("oo"));
        assertThat(word, containsStringIgnoringCase("OOp"));


        // blank String - Empty String
        String str = " ";
        assertThat(str, is(blankString()));
        assertThat(str.replace(" ", ""), is(emptyString()));
        // String str; // -- > null
        assertThat(str.replace(" ", ""), is(emptyOrNullString()));
        assertThat(str.trim(), is(emptyString()));


    }


    @Test
    public void listsTest () {

        List <Integer> nums = Arrays.asList(5, 20, 1, 54, 0);
        List <String> words = Arrays.asList("java", "selenium", "git", "maven", "api");


        // size
        assertThat(nums, hasSize(5));
        assertThat(words, hasSize(5));


        // contains item
        assertThat(nums, hasItem(20));
        // assertThat(nums, hasItem(7)); // this will fail because 7 is not among the options


        assertThat(words, hasItem("git"));
        assertThat(words, hasItems("git", "api")); // ALL the provided parameters have to be in list to eb true - for positive test
        // assertThat(words, hasItems("git", "db"));

        // assertThat(nums, containsInAnyOrder(5, 20, 1, 54)); // size is not matching - has to be same size - Also compares size
        assertThat(nums, containsInAnyOrder(54, 20, 0, 5, 1)); // We need to provide all the elements and same size for it to be true


        // every element
        // assertThat(nums, everyItem(greaterThan(0)));
        assertThat(nums, everyItem(greaterThanOrEqualTo(0)));
        assertThat(words, everyItem( is( not( blankString()))));

    }

}

