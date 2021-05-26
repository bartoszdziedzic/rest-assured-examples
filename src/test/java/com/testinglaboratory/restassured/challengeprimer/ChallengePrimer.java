package com.testinglaboratory.restassured.challengeprimer;

import io.restassured.RestAssured;
import io.restassured.response.ResponseBodyExtractionOptions;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@Slf4j
public class ChallengePrimer {

    String userName = "yodamaster";
    String userPassword = "dupadupa";
    String userNameWrong = "a";
    String userPasswordWrong = "a";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8082/";
        RestAssured.basePath = "challenge/primer/";
    }

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
    //moze opierdzielic to jakims if'em?
    @Test
    void getFlagById(){
        when().get("flag/1")
                .then().log().everything();
        when().get("flag/6")
                .then().log().everything();
    }

    @Test
    void postRegisterSuccessful(){
        JSONObject newUser= new JSONObject();
        newUser.put("username", userName);
        newUser.put("password", userPassword);

        ResponseBodyExtractionOptions responseBody =
        given()
                .header("Content-Type", "application/json")
                .when()
                .body(newUser.toMap())
                .post("/register")
                .then()
                .log().everything().extract().body();
        System.out.println(responseBody.jsonPath().getString("key"));;
    }

    //"${flag_im_still_here_captain}"
    @Test
    void postRegisterAgain(){
        JSONObject newUser= new JSONObject();
        newUser.put("username", userName);
        newUser.put("password", userPassword);

        given()
            .header("Content-Type", "application/json")
            .when()
            .body(newUser.toMap())
            .post("/register")
            .then()
            .statusCode(400)
            .log().everything();
    }

    @Test
    void postLogin(){
        JSONObject newUser= new JSONObject();
        newUser.put("username", userName);
        newUser.put("password", userPassword);

        given()
                .header("Content-Type", "application/json")
                .when()
                .body(newUser.toMap())
                .post("/login")
                .then()
                .statusCode(202)
                .log().everything();
    }

    //"${flag_naughty_aint_ya}"
    @Test
    void postLoginUnsuccessful(){
        JSONObject newUser= new JSONObject();
        newUser.put("username", userNameWrong);
        newUser.put("password", userPasswordWrong);

        given()
                .header("Content-Type", "application/json")
                .when()
                .body(newUser.toMap())
                .post("/login")
                .then()
                .statusCode(401)
                .log().everything();
    }
}