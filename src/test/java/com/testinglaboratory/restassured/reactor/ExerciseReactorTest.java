package com.testinglaboratory.restassured.reactor;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;

//TODO EXERCISE create tests for Reactor challenge
@Slf4j
class ExerciseReactorTest extends BaseSetUp{

    @Test
    void checkInformation(){
        when().get("information")
                .then()
                .log()
                .everything()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("message", equalTo(informationMessage));
    }

    @Test
    void checkInAtDesk(){
        Response response = Register.registerUser(user);
        assertThat(response.jsonPath().getString("message")).isEqualTo(registerMessage);
    }

    @Test
    void walkIntoControlRoom(){
        Response response = acquireControlRoomCredentials();
        assertThat(response.jsonPath().getString("message")).isEqualTo("Hello " + user.name);
        assertThat(response.jsonPath().getString("'reactor data'._ReactorCore__state")).isEqualTo("Operational");
    }

    @Test
    void walkIntoControlRoomUnsuccessfully(){

        when()
                .get("1/control_room")
                .then()
                .log()
                .everything()
                .assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message",equalTo("You're can't get pass this door comrade! ${flag_sneaky_rat}"));
    }

    @Test
    void lookIntoReactor(){
        checkReactorStatus();
    }

    @Test
    void lookIntoReactorUnsuccessful(){
        when()
                .get("fake_key/reactor_core")
                .then()
                .log()
                .everything()
                .assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message",equalTo("He's not a Tech Commander!" +
                        " Meddling with Power Plant! Get him to KGB!!!"));
    }

    @Test
    void reactorAnalysis(){
        analyseReactor();
    }

    @Test
    void reactorAnalysisUnsuccessful(){
        when()
                .get("fake_key/control_room/analysis")
                .then()
                .log()
                .everything()
                .assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message",equalTo("He's not a Tech Commander!" +
                        " Meddling with Power Plant! Get him to KGB!!!"));
    }

    @Test
    void resetProgress(){
        resetReactor();
    }

    @Test
    void resetProgressUnsuccessful(){
        when()
                .get("1/reset_progress")
                .then()
                .log()
                .everything()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("flag",equalTo("${flag_atomna_elektrostancja_erector}"));
    }

    @Test
    void meddleWithControlRodsUntilUnstable(){
        Response response = acquireControlRoomCredentials();
        String key = response.jsonPath().getString("'reactor data'._ReactorCore__uuid");
        List<String> controlRods= response.jsonPath().getList("'reactor data'._ReactorCore__control_rods");
        List<String> fuelRods= response.jsonPath().getList("'reactor data'._ReactorCore__fuel_rods");
        Integer power = response.jsonPath().getInt("'reactor data'._ReactorCore__power");
        String reactorState = response.jsonPath().getString("'reactor data'._ReactorCore__state");
//dodac do pętli zerkanie w reaktor w celu sprawdzenia statusu
            for (int rodIndex = 0; rodIndex < 50; rodIndex++) {
                if(!reactorState.equals("Unstable")){
                given().pathParam(
                        "key", key).pathParam(
                        "rodIndex", rodIndex)
                        .when()
                        .delete("{key}/control_room/control_rods/{rodIndex}");
                }
        }
            assertThat(response.jsonPath().getString("'reactor data'._ReactorCore__state")).isEqualTo("Unstable");
    }

    final String informationMessage = "You are the Tech Commander of RBMK reactor power plant. " +
            "Your task is to perform the reactor test. Bring the power level above 1000 but below 1500" +
            " and keep the reactor Operational. Use /{key}/control_room/analysis to peek at reactor core." +
            " Use /{key}/control_room to see full info about the reactor. Check in at the /desk to get your" +
            " key to control room. Put in fuel rods or pull out control rods to raise the power." +
            " Put in control rods or pull out fuel rods to decrease the power. There are 12 flags to find." +
            " Good luck Commander. ";

    final String registerMessage = "Take the key to your control room. " +
            "Keep it safe. use it as resource path to check on your RMBK-100 reactor! Use following: " +
            "/{key}/control_room to gain knowledge how to operate reactor. You may see if the " +
            "core is intact here: /{key}/reactor_core . If anything goes wrong push AZ-5 safety " +
            "button to put all control rods in place!Good luck Commander.";

    private Response acquireControlRoomCredentials(){
        Response response = Register.registerUser(user);

        String key = response.jsonPath().get("key");

        response = when()
                .get(key + "/control_room")
                .then()
                .log()
                .everything()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
        return response;
    }

    private void checkReactorStatus(){
        Response response = Register.registerUser(user);

        String key = response.jsonPath().get("key");

        response = when()
                .get(key + "/reactor_core")
                .then()
                .log()
                .everything()
                .assertThat()
                .statusCode(HttpStatus.SC_OK).extract().response();

        assertThat(response.jsonPath().getString("flag"))
                .isEqualTo("${flag_curious_arent_we_" + user.name + "}");
    }

    private void analyseReactor(){
        Response response = Register.registerUser(user);

        String key = response.jsonPath().get("key");

        when()
                .get(key + "/control_room/analysis")
                .then()
                .log()
                .everything()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
    }

    private void resetReactor(){
        Response response = Register.registerUser(user);

        String key = response.jsonPath().get("key");

        response = when()
                .get(key + "/reset_progress")
                .then()
                .log()
                .everything()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
        assertThat(response.jsonPath().getString("flag"))
                .isEqualTo("${flag_you_didnt_see_the_graphite_because_its_not_there}");
    }
}