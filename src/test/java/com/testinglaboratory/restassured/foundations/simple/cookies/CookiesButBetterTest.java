package com.testinglaboratory.restassured.foundations.simple.cookies;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@Slf4j
public class CookiesButBetterTest {

    @Test
    public void shouldBeRegistered() {
        JsonObject user = createUser();
        log.info(user.toString());
        Response response = registerUser(user);
        String kluczyk = response.body().jsonPath().getString("key");
        System.out.println(kluczyk);
        Map<String, String> ciacha = response.cookies();
        System.out.println(ciacha);
    }

    @Test
    public void shouldBeAbleToLogin() {
        JsonObject user = createUser();
        log.info(user.toString());
        registerUser(user);
        Response loginResponse = loginUser(user);
        Map<String, String> cookies = loginResponse.cookies();
        accessRestrictedResource(cookies)
                .then()
                .statusCode(200);
    }


    @Test
    public void shouldNotBeAbleToAccessResourceWhenNotLoggedIn() {
        JsonObject user = createUser();
        log.info(user.toString());
        registerUser(user);
        Map<String, String> cookies = new HashMap<>();
        accessRestrictedResource(cookies)
                .then()
                .statusCode(401);
    }


    private JsonObject createUser(){
        Faker fake = new Faker(new Locale("PL_pl"));
        String username = fake.name().username();
        String password = fake.internet().password();
        JsonObject user = new JsonObject();
        user.addProperty("username", username);
        user.addProperty("password", password);
        return user;
    }

    private Response registerUser(JsonObject user){
        return given()
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .body(user)
                .when()
                .post("/register")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(201)
                .body(containsString(String.valueOf(user.get("username"))))
                .extract().response();
    }

    private Response loginUser(JsonObject user){
        return given()
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .body(user)
                .when()
                .post("/login")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(202)
                .body(containsString(String.valueOf(user.get("username"))))
                .extract().response();
    }
    private Response accessRestrictedResource(Map<String, String> cookies){
        return given()
                .header("accept", "application/json")
                .cookies(cookies)
                .when()
                .get("/for_logged_in_users_only")
                .then()
                .log().everything()
                .extract().response();

    }
}
