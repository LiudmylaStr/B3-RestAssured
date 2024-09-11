package io.loopcamp.pojo;
import lombok.Data;

@Data
public class Minion {
    private int id;
    private String gender;
    private String name;
    private String phone;

    // public String lastName;  // if you have extra KEY:VALUE in JSON response body, but you did not provide it in POJO class, it will not give an error.
    // public int age;  //Possible new updates: If you have extra instance variable in POJO class, but not in JSON response body, will not give an error either
    // All the KEY from JSON response must match the POJO class instance variables names

}





/*
    // JSON RESPONSE BODY
     {
        "id": 10,
        "gender": "Female",
        "name": "Lorenza",
        "phone": "3312820936"
    }
 */

/*
    //Minion minionObj = response.as(Minion.class); // it does the action below.
    public Minion (int id, String gender, String name, String phone){
        this.id = id;
        this.gender = gender;
        this.name = name;
        this.phone = phone;
    }


    //@Data --- > from LOMBOK dependency/library --- > helps with ENCAPSULATION
    public int getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

 */

/*
    EXTRA:
    {
        "post code": "22031",
        "country": "United States",
        "country abbreviation": "US"
    }

    // If you KEY is not following variable naming rules, we can use @JsonProperty("") annotation
    @JsonProperty("post code")
    public String postCode;
 */

