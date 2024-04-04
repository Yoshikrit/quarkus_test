package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.acme.model.JwtResponse;
import org.acme.model.UserLogin;
import org.acme.resource.JwtResource;
import org.acme.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class JwtTestIT {
    @Inject
    JwtService jwtService;
    @Inject
    JwtResource jwtResource;

    private String email;
    private String password;
    private UserLogin userLogin;
    private Response response;
    private JwtResponse jwtResponse;
    private String role;

    @BeforeEach
    void setUp() {
        userLogin = new UserLogin("a", "a");
        email = "a";
        password = "a";
        role = "Manager";
    }

    @Test
    void loginUserOK() {
        jwtResponse = jwtService.getJWT(email, password);
        response = jwtResource.loginUser(userLogin);

        //Assert
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }
}
