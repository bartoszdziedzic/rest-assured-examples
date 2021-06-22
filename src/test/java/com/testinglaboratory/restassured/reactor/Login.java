package com.testinglaboratory.restassured.reactor;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Login extends Register{
    protected static Response loginUser(ReactorUser user){
        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(user)
                .post("/login")
                .then()
                .log().everything().extract().response();
    }
}
