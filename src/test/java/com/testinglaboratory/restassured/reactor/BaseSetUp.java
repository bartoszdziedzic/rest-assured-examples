package com.testinglaboratory.restassured.reactor;

import com.github.javafaker.Faker;
import com.testinglaboratory.restassured.challengeprimer.User;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.Locale;

public class BaseSetUp {

    protected static final Faker fake = new Faker(new Locale("en", "US"));
    protected ReactorUser user;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8083/";
        RestAssured.basePath = "challenge/reactor/";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();
    }

    @BeforeEach
    protected void generateCredentials() {
        user = new ReactorUser(fake.name().username());
    }
}