package com.testinglaboratory.restassured.reactor;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Register {
    protected static Response registerUser(ReactorUser user){
        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(user)
                .post("/desk");
    }
}
