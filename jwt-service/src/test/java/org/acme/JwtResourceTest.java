package org.acme;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.acme.error.ErrorMessage;
import org.acme.error.ErrorResponse;
import org.acme.model.JwtResponse;
import org.acme.model.UserLogin;
import org.acme.resource.JwtResource;
import org.acme.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class JwtResourceTest {
    @InjectMock
    JwtService jwtService;
    @Inject
    JwtResource jwtResource;

    private UserLogin userLogin;
    private String jwt;
    private JwtResponse jwtResponse;
    private ErrorResponse errorResponse;

    @BeforeEach
    void setUp() {
        userLogin = new UserLogin("a", "a");
        jwt = "JWT";
        errorResponse = new ErrorResponse("401", new ErrorMessage("Authentication failed"));
    }

    @Test
    void loginUserEndpointOK() {
        jwtResponse = new JwtResponse(jwt);
        //Arrange
        Mockito.when(jwtService.getJWT(userLogin.getEmail(), userLogin.getPassword())).thenReturn(jwtResponse);
        //Act
        Response response = jwtResource.loginUser(userLogin);

        //Assert
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        String jwt = (String) response.getEntity();
        assertEquals("JWT", jwt);
    }

    @Test
    void loginUserEndpointKO() {
        jwtResponse = new JwtResponse(errorResponse);
        //Arrange
        Mockito.when(jwtService.getJWT(userLogin.getEmail(), userLogin.getPassword())).thenReturn(jwtResponse);
        //Act
        Response response = jwtResource.loginUser(userLogin);

        //Assert
        assertNotNull(response);
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ErrorResponse entity = (ErrorResponse) response.getEntity();
        assertEquals("401", entity.getErrorId());
        assertEquals("Authentication failed", entity.getErrors().get(0).getMessage());
    }
}
