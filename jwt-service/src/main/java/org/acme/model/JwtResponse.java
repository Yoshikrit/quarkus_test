package org.acme.model;

import org.acme.error.ErrorResponse;

public class JwtResponse {
    private Boolean isError;
    private String jwt;
    private ErrorResponse errorResponse;

    public JwtResponse(){}
    public JwtResponse(String jwt){
        isError = false;
        this.jwt = jwt;
    }

    public JwtResponse(ErrorResponse errorResponse){
        isError = true;
        this.errorResponse = errorResponse;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
