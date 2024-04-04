package org.acme.error;

import org.acme.error.ErrorMessage;

import java.util.List;

public class ErrorResponse {
    private String errorId;
    private List<ErrorMessage> errors;

    public ErrorResponse(){}

    public ErrorResponse(String errorId, ErrorMessage errorMessage){
        this.errorId = errorId;
        this.errors = List.of(errorMessage);
    }

    public ErrorResponse(ErrorMessage errorMessage) {
        this(null, errorMessage);
    }

    public ErrorResponse(String errorId, List<ErrorMessage> errors){
        this.errorId = errorId;
        this.errors = errors;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public List<ErrorMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorMessage> errors) {
        this.errors = errors;
    }

    public void addErrorMessage(ErrorMessage errorMessage) {
        errors.add(errorMessage);
    }
}
