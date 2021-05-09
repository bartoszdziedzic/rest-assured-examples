package com.testinglaboratory.restassured.foundations.simple.cookies;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

@Slf4j
public class CookiesTest {

    @Test
    public void shouldBeRegistered() {
        Faker fake = new Faker(new Locale("PL_pl"));
        String username = fake.name().username();
        String password = fake.internet().password();
        JsonObject user = new JsonObject();
        user.addProperty("username", username);
        user.addProperty("password", password);

        log.info(user.toString());
        ExtractableResponse<Response> response = given()
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .body(user)
                .when()
                .post("/register")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(201)
                .body(containsString(username))
                .extract();
        String kluczyk = response.body().jsonPath().getString("key");
        System.out.println(kluczyk);
        Map<String, String> ciacha = response.cookies();
        System.out.println(ciacha);
    }

    @Test
    public void shouldBeAbleToLogin() {
        Faker fake = new Faker(new Locale("PL_pl"));
        String username = fake.name().username();
        String password = fake.internet().password();
        JsonObject user = new JsonObject();
        user.addProperty("username", username);
        user.addProperty("password", password);
        log.info(user.toString());
        given()
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .body(user)
                .when()
                .post("/register")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(201)
                .body(containsString(username));

        ExtractableResponse<Response> loginResponse = given()
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .body(user)
                .when()
                .post("/login")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(202)
                .body(containsString(username)).extract();

        System.out.println(loginResponse.cookies());
        System.out.println(loginResponse.body());

        Map<String, String> cookies = loginResponse.cookies();

        given()
                .header("accept", "application/json")
                .cookies(cookies)
                .when()
                .get("/for_logged_in_users_only")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(200);

    }


    @Test
    public void shouldNotBeAbleToAccessResourceWhenNotLoggedIn() {
        Faker fake = new Faker(new Locale("PL_pl"));
        String username = fake.name().username();
        String password = fake.internet().password();
        JsonObject user = new JsonObject();
        user.addProperty("username", username);
        user.addProperty("password", password);
        log.info(user.toString());
        given()
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .body(user)
                .when()
                .post("/register")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(201)
                .body(containsString(username));

        ExtractableResponse<Response> loginResponse = given()
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .body(user)
                .when()
                .post("/login")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(202)
                .body(containsString(username)).extract();

        System.out.println(loginResponse.cookies());
        System.out.println(loginResponse.body());

        Map<String, String> cookies = loginResponse.cookies();

        given()
                .header("accept", "application/json")
                .when()
                .get("/for_logged_in_users_only")
                .then()
                .log().everything()
                .assertThat()
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
}
