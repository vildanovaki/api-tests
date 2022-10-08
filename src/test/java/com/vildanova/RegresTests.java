package com.vildanova;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.is;

public class RegresTests extends TestBase {

    @Test
    void successfulRegistration() {
        String data = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/register")
                .then()
                .statusCode(200)
                .body("id", is(4), "token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulRegistration() {
        String data = "{ \"email\": \"sydney@fife\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void updateInformationAboutUser() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .put("/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"));
    }

    @Test
    void userNotFound() {
        given()
                .get("/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    void listOfResources() {
        String response =
                get("/api/unknown")
                        .then()
                        .extract().response().asString();

        assertThat(response.contains("aqua sky"));
    }
}
