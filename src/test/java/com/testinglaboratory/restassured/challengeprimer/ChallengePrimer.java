package com.testinglaboratory.restassured.challengeprimer;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ChallengePrimer extends BaseTest {

    @Test
    void getInformation(){
       when()
               .get("information")
               .then()
               .log()
               .everything().assertThat().statusCode(HttpStatus.SC_OK)
               .body("message",equalToCompressingWhiteSpace(information));
    }

    @Test
    void getTryout(){
        when()
                .get("tryout")
                .then()
                .log()
                .everything().assertThat().statusCode(HttpStatus.SC_OK)
                .body("message", equalTo("Good! " +
                "Toy have tried to GET a resource.Now you have to GET something else... /flag"));
    }

    @Test
    void getFlag(){
        when()
                .get("flag")
                .then()
                .log()
                .everything().assertThat().statusCode(HttpStatus.SC_OK)
                .body("message",equalTo("Use your exploratory skills" +
                        " and feel the challenge's theme to obtain flags"));
    }

    //"${flag_hello_there}" & "${flag_general_kenobi}"
    @Test
    void getFlagById(){
        when()
                .get("flag/1")
                .then()
                .log()
                .everything().assertThat().statusCode(HttpStatus.SC_OK)
                .body("flag",equalTo("${flag_hello_there}"));

        when()
                .get("flag/6")
                .then()
                .log()
                .everything().assertThat().statusCode(HttpStatus.SC_OK)
                .body("flag",equalTo("${flag_general_kenobi}"));
    }

    @ParameterizedTest
    @DisplayName("Check which id's have flags")
    @ValueSource(ints = {1,6})
    void checkTwoFlags(int flagID) {
        Response response = given()
                .pathParam("flagId",flagID)
                .when()
                .get("flag/{flagId}")
                .then()
                .log()
                .everything()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        assertThat(response.jsonPath().getString("flag")).matches("\\$\\{flag_.*}");
    }

    @Test
    void postRegisterSuccessful(){
        Response response = RegisterUser.registerUserMethod(user);
        assert response.jsonPath().get("message").equals("User " + user.getUsername() + " registered");
 }

    //"${flag_im_still_here_captain}"
    @Test
    void postRegisterAgain(){
        RegisterUser.registerUserMethod(user);
        Response response = RegisterUser.registerUserMethod(user).then().log().everything().extract().response();
        assert response.jsonPath().get("flag").equals("${flag_im_still_here_captain}");
    }

    @Test
    void postLogin(){
        RegisterUser.registerUserMethod(user);
        Response response = LoginUser.loginUserMethod(user);
        assert response.jsonPath().get("message").equals("Welcome, " + user.getUsername() + ", in the Primer!");
    }

//    "${flag_naughty_aint_ya}"
    @Test
    void postLoginUnsuccessful(){
        assert LoginUser.loginUserMethod(user).jsonPath().get("flag").equals("${flag_naughty_aint_ya}");
    }

    String information = "Oi! W'at can I do for ya? In this primer for challenges" +
            " you'll learn how to look for flags. Remember that this is not purely" +
            " technical task. You'll role play and use your knowledge to find treasures" +
            " your looking for. If you have any questions - ask. Try and found as many" +
            " flags as possible.(Five, there are five.) begin with shooting at /tryout.";
}