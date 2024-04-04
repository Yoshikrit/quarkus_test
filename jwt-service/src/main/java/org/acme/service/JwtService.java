package org.acme.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.error.ErrorMessage;
import org.acme.error.ErrorResponse;
import org.acme.model.JwtResponse;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class JwtService {
    private final Logger LOGGER = Logger.getLogger(JwtService.class);

    public JwtResponse getJWT(String email, String password){
        String authenUser = authenticateUser(email, password);

        if(authenUser.isEmpty()) {
            return new JwtResponse(new ErrorResponse("401", new ErrorMessage("Authentication failed")));
        }
        String jwt = generateJWT(authenUser);
        return new JwtResponse(jwt);
    }

    public String authenticateUser(String email, String password){
        if(email.isEmpty() || password.isEmpty()){
            return "";
        }
        return "Manager";
    }

    public String generateJWT(String role){
        long duration = (System.currentTimeMillis()/1000) + 3600;
        LOGGER.info("Create Json Web Token Successful");
        return Jwt.issuer("jwt-service")
                .subject("product-service")
                .groups(new HashSet<>(
                        Arrays.asList(role)
                ))
                .expiresAt(duration)
                .sign();
    }
}
