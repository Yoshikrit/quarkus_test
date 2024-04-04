package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@Tag("Integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTypeTestETE {
    @Test
    @Order(1)
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void getProductTypesEndpointOK() {
        given()
                .when()
                .get("/producttype/")
                .then()
                .body("size()", equalTo(3),
                        "prodTypeId", hasItems(1, 2, 3),
                        "prodTypeName", hasItems("Food", "Drink", "Snack")
                )
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    void getProductTypesEndpointUnauthorized() {
        given()
                .when()
                .get("/producttype/")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @Order(1)
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void getProductTypeByIdEndpointOK() {
        given()
                .when()
                .get("/producttype/1")
                .then()
                .body("prodTypeId", equalTo(1),
                        "prodTypeName", equalTo("Food")
                )
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void getProductTypeByIdEndpointKO() {
        given()
                .when()
                .get("/producttype/0")
                .then()
                .body("errorId", equalTo("404"))
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(1)
    void getProductTypeByIdEndpointUnauthorized() {
        given()
                .when()
                .get("/producttype/0")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @Order(2)
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void createProductTypeEndpointOK() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("prodTypeId", "4")
                        .add("prodTypeName", "Hes")
                        .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/producttype/")
                .then()
                .body("prodTypeId", equalTo(4),
                        "prodTypeName", equalTo("Hes")
                )
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    @Order(2)
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void createProductTypeEndpointKO1() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("prodTypeId", "1")
                        .add("prodTypeName", "Hes")
                        .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/producttype/")
                .then()
                .body("errorId", equalTo("409"),
                        "errors[0].field", equalTo("Cant Create ProductType"),
                        "errors[0].message", equalTo("Id : 1 Is Conflict")
                )
                .statusCode(Response.Status.CONFLICT.getStatusCode());
    }

    @Test
    @Order(2)
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void createProductTypeEndpointKO2() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("prodTypeId", "4")
                        .add("prodTypeName", "")
                        .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/producttype/")
                .then()
                .body("errorId", equalTo("400"))
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @Order(2)
    void createProductTypeEndpointUnauthorized() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("prodTypeId", "4")
                        .add("prodTypeName", "")
                        .build();
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/producttype/")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @Order(3)
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void updateProductTypeEndpointOK() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("prodTypeName", "Has")
                        .build();
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .put("/producttype/4")
                .then()
                .body("prodTypeId", equalTo(4),
                        "prodTypeName", equalTo("Has")
                )
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(3)
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void updateProductTypeEndpointKO1() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("prodTypeName", "Has")
                        .build();
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .put("/producttype/0")
                .then()
                .body("errorId", equalTo("404"))
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(3)
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void updateProductTypeEndpointKO2() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("prodTypeName", "")
                        .build();
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .put("/producttype/4")
                .then()
                .body("errorId", equalTo("400"))
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @Order(3)
    void updateProductTypeEndpointUnauthorized() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("prodTypeName", "a")
                        .build();
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .put("/producttype/4")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @Order(4)
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void deleteProductTypeEndpointOK() {
        given()
                .when()
                .delete("/producttype/4")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(4)
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void deleteProductTypeEndpointKO() {
        given()
                .when()
                .delete("/producttype/0")
                .then()
                .body("errorId", equalTo("404"))
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(4)
    void deleteProductTypeEndpointUnauthorized() {
        given()
                .when()
                .delete("/producttype/0")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }
}
