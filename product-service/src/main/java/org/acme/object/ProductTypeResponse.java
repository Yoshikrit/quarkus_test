package org.acme.object;

import org.acme.error.ErrorResponse;

import java.util.List;

public class ProductTypeResponse {
    private Boolean isError;
    private ProductType productType;
    private List<ProductType> productTypeList;
    private ErrorResponse errorResponse;

    public ProductTypeResponse(){}

    public ProductTypeResponse(Boolean isError){
        this.isError = isError;
    }

    public ProductTypeResponse(ProductType productType){
        isError = false;
        this.productType = productType;
    }

    public ProductTypeResponse(List<ProductType> productTypeList){
        isError = false;
        this.productTypeList = productTypeList;
    }
    public ProductTypeResponse(ErrorResponse errorResponse){
        isError = true;
        this.errorResponse = errorResponse;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public List<ProductType> getProductTypeList() {
        return productTypeList;
    }

    public void setProductTypeList(List<ProductType> productTypeList) {
        this.productTypeList = productTypeList;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
