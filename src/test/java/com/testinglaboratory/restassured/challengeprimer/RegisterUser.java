package com.testinglaboratory.restassured.challengeprimer;

import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class RegisterUser {

    protected static Response registerUserMethod(User user) {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(user)
                .post("/register");
    }
}