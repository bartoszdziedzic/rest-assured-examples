package com.testinglaboratory.restassured.foundations.simple.basicauth;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BasicAuthTest {

    private final String BAD_USERNAME = "gibberish";
    private final String BAD_PASS = "gibberish";
    private final String GOOD_USERNAME = "SeniorSiarra";
    private final String GOOD_PASS = "JurekKiler";

    @Test
    public void shouldNotBeAuthenticatedWithoutCredentials() {
        given().auth().basic(BAD_USERNAME, BAD_PASS)
                .when()
                .get("/users/me")
                .then()
                .statusCode(401).log().everything();

    }

    @Test
    public void shouldBeAbleToAuth() {
        given().auth().basic(GOOD_USERNAME, GOOD_PASS)
                .when()
                .get("/users/me")
                .then()
                .statusCode(200)
                .log().everything();
    }

    @Test
    public void shouldAccessResource() {
        given().auth().basic(GOOD_USERNAME, GOOD_PASS)
                .when()
                .get("/users/some_secret_resource/1")
                .then()
                .statusCode(200)
                .log().everything();
    }

    @Test
    public void shouldGetKickedOut() {
        given().auth().basic(BAD_USERNAME, BAD_PASS)
                .when()
                .get("/users/some_secret_resource/1")
                .then()
                .statusCode(401)
                .log().everything();
    }
}
