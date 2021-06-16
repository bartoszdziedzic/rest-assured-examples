package com.testinglaboratory.restassured.challengeprimer;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static final Faker fake = new Faker(new Locale("PL_pl"));
    protected User user;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8082/";
        RestAssured.basePath = "challenge/primer/";
    }

    @BeforeEach
    protected void generateCredentials() {
        user = new User(fake.name().username(), fake.yoda().quote());
    }
}