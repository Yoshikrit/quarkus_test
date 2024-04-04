package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.core.Response;
import org.acme.error.ErrorMessage;
import org.acme.error.ErrorResponse;
import org.acme.model.JwtResponse;
import org.acme.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class JwtServiceTest {
    @Inject
    JwtService jwtService;

    private String email;
    private String password;
    private String role;
    private JwtResponse jwtResponse;
    private ErrorResponse errorResponse;

    @BeforeEach
    void setUp() {
        email = "a";
        password = "a";
        role = "Manager";
    }

    @Test
    void authenticateUserOK() {
        String roleRes = jwtService.authenticateUser(email, password);

        assertNotNull(roleRes);
        assertEquals("Manager", roleRes);
    }


    @Test
    void authenticateUserKO() {
        String roleRes = jwtService.authenticateUser(email, "");

        assertNotNull(roleRes);
        assertEquals("", roleRes);
    }

    @Test
    void generateJWTOK(){
        String jwtRes = jwtService.generateJWT(role);

        assertNotNull(jwtRes);
    }

    @Test
    void testGetJWTAuthenticationOK() {

        jwtResponse = jwtService.getJWT(email, password);

        assertNotNull(jwtResponse);
        assertEquals(false, jwtResponse.getError());
        String jwt = jwtResponse.getJwt();
        assertNotNull(jwt);
    }

    @Test
    void testGetJWTAuthenticationKO() {
        jwtResponse = jwtService.getJWT(email, "");

        assertNotNull(jwtResponse);
        assertEquals(true, jwtResponse.getError());
        errorResponse = jwtResponse.getErrorResponse();
        assertEquals("401", errorResponse.getErrorId());
        assertEquals("Authentication failed", errorResponse.getErrors().get(0).getMessage());
    }
}
