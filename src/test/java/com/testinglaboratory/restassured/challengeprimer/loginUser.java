package com.testinglaboratory.restassured.challengeprimer;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class loginUser extends registerUser{

    protected Response loginUserMethod(String userName, String userPassword) {

        JSONObject newUser = new JSONObject(new baseTest(userName,userPassword));
        newUser.put("username", userName);
        newUser.put("password", userPassword);
        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(newUser.toMap())
                .post("/register")
                .then()
                .log().everything().extract().response();
    }
}