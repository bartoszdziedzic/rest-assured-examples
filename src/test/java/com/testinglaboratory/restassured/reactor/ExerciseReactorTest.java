package com.testinglaboratory.restassured.reactor;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;

//TODO EXERCISE create tests for Reactor challenge
public class ExerciseReactorTest extends BaseSetUp{

    String registerMessage = "Take the key to your control room. " +
            "Keep it safe. use it as resource path to check on your RMBK-100 reactor! Use following: " +
            "/{key}/control_room to gain knowledge how to operate reactor. You may see if the " +
            "core is intact here: /{key}/reactor_core . If anything goes wrong push AZ-5 safety " +
            "button to put all control rods in place!Good luck Commander.";

    @Test
    void getInformation(){
        when().get("information")
                .then()
                .log()
                .everything();
    }

    @Test
    void checkIn(){
        Response response = Register.registerUser(user);

        assert response.jsonPath().get("message").equals(registerMessage);
    }

    @Test
    void getControlRoom(){
        Response response = Register.registerUser(user);

        String key = response.jsonPath().get("key");

        when()
                .get(key + "/control_room")
                .then()
                .log()
                .everything()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }
}
