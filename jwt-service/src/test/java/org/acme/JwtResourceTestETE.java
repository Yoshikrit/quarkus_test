package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JwtResourceTestETE {
    @Test
    @Order(1)
    void loginUserEndpointOK() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("email", "a")
                        .add("password", "a")
                        .build();
        given()
            .when()
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonObject.toString())
            .post("/login/")
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    void loginUserEndpointKO() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("email", "a")
                        .add("password", "")
                        .build();
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .post("/login/")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }
}
