package org.acme.object;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class UpdateProductType {

    @NotEmpty
    @NotBlank(message="ProductType 's name is required.")
    private String prodTypeName;

    public UpdateProductType(){}

    public UpdateProductType(String prodTypeName){
        this.prodTypeName = prodTypeName;
    }

    public String getProdTypeName() {
        return prodTypeName;
    }

    public void setProdTypeName(String prodTypeName) {
        this.prodTypeName = prodTypeName;
    }
}
