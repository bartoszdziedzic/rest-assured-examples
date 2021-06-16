package com.testinglaboratory.restassured.challengeprimer;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@Slf4j
public class ChallengePrimer extends BaseTest {

    @Test
    void getInformation(){
       when().get("information")
       .then().log().everything();
    }

    @Test
    void getTryout(){
        when().get("tryout")
                .then().log().everything();
    }

    @Test
    void getFlag(){
        when().get("flag")
                .then().log().everything();
    }

    //"${flag_hello_there}" & "${flag_general_kenobi}"
    @Test
    void getFlagById(){
        when().get("flag/1")
                .then().log().everything();
        when().get("flag/6")
                .then().log().everything();
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
}