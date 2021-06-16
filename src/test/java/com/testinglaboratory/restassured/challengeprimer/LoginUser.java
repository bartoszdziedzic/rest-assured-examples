package com.testinglaboratory.restassured.challengeprimer;

import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class LoginUser extends RegisterUser {

    protected static Response loginUserMethod(User user) {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(user)
                .post("/login")
                .then()
                .log().everything().extract().response();
    }
}