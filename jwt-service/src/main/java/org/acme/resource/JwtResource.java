package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.JwtResponse;
import org.acme.model.UserLogin;
import org.acme.service.JwtService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/login")
@Tag(name = "JWT", description = "Operation about generating JWT")
public class JwtResource {
    @Inject
    JwtService jwtService;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "Get JWT",
            summary = "Get JWT",
            description = "Get JWT"
    )
    public Response loginUser(UserLogin userLogin){
        JwtResponse result = jwtService.getJWT(userLogin.getEmail(), userLogin.getPassword());
        if(result.getError()) {
            return Response.status(Integer.parseInt(result.getErrorResponse().getErrorId()))
                    .entity(result.getErrorResponse())
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(result.getJwt())
                .build();
    }
}