package com.testinglaboratory.restassured.reactor;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class Register {
    static Response registerUser(ReactorUser user){
        return given()
                .body(user)
                .when()
                .post("/desk")
                .then()
                .log()
                .everything()
                .extract()
                .response();
    }
}