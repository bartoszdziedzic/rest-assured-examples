package com.testinglaboratory.restassured.foundations.simple.headers;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

@Slf4j
public class HeaderTest {

    @Test
    public void shouldBeAuthenticated() {
        Response response =
                given()
                        .headers("apikey", "woohoo")
                .when()
                        .get("/header_check")
                        .then()
                        .statusCode(403)
                        .body("basicInformation", equalTo("This is GET example for status code 403"),
                                "responseInformation.shortDescription", equalTo("Forbidden"),
                                "responseInformation.longDescription",
                                equalTo("The client does not have access rights to the content; that is, it is unauthorized, so the server is refusing to give the requested resource.")
                        ).extract().response();
        log.info(response.headers().toString());
    }


    @Test
    public void shouldNotBeAuthenticated() {
        Response response =

                when()
                        .get("/header_check")
                        .then()
                        .statusCode(403)
                        .body("basicInformation", equalTo("This is GET example for status code 403"),
                                "responseInformation.shortDescription", equalTo("Forbidden"),
                                "responseInformation.longDescription",
                                equalTo("The client does not have access rights to the content; that is, it is unauthorized, so the server is refusing to give the requested resource."),
                                "responseInformation.whoopsie", equalTo("Probably you should ask for the auth key")
                        ).extract().response();
        log.info(response.headers().toString());
    }
}
