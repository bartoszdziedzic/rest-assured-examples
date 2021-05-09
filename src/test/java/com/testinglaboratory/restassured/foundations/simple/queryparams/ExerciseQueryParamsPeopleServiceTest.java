package com.testinglaboratory.restassured.foundations.simple.queryparams;

//TODO exercise query parameters

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Create tests for:
 * /get_all_people_sliced
 * /get_all_people_paged
 * /get_all_people_by
 * endpoints
 */
@Slf4j
public class ExerciseQueryParamsPeopleServiceTest {

    @Test
    public void shouldReturnAllPeopleSliced() {
        Response response = given().queryParam("from_number", "3")
                .queryParam("up_to_number", "6")
                .when()
                .get("/get_all_people_sliced")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();
        List<Map<String, String>> people = response.body().jsonPath().getList(".");
        log.info(people.toString());
        assertThat(people).hasSize(3);
    }

    @Test
    public void shouldReturnAllPeoplePaged() {
        Response response = given().queryParam("page_size", "2")
                .queryParam("page_number", "1")
                .when()
                .get("/get_all_people_paged")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();
        List<Map<String, String>> people = response.body().jsonPath().getList(".");
        log.info(people.toString());
        assertThat(people).hasSize(2);
    }

    @Test
    public void shouldReturnAllPeopleBy() {
        Response response = given().queryParam("first_name", "Monika")
                .when()
                .get("/get_people_by")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();
        List<Map<String, String>> people = response.body().jsonPath().getList(".");
        log.info(people.toString());
        people.forEach(stringStringMap -> assertThat(stringStringMap.get("first_name")).isEqualTo("Monika"));
    }
}