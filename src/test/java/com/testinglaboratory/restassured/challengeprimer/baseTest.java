package com.testinglaboratory.restassured.challengeprimer;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class baseTest {

    protected static final Faker fake = new Faker(new Locale("PL_pl"));
    protected String userName;
    protected String userPassword;

    public baseTest(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8082/";
        RestAssured.basePath = "challenge/primer/";
    }

    @BeforeEach
    protected void generateCredentials() {
        userName = fake.name().username();
        userPassword = fake.yoda().quote();
    }
}