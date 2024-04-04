package org.acme.object;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class ProductType {

    private int prodTypeId;
    @NotEmpty
    @NotBlank(message="ProductType's name is required.")
    private String prodTypeName;

    public ProductType(){}

    public ProductType(int prodTypeId,
                       String prodTypeName) {
        this.prodTypeId = prodTypeId;
        this.prodTypeName = prodTypeName;
    }

    public int getProdTypeId() {
        return prodTypeId;
    }

    public void setProdTypeId(int prodTypeId) {
        this.prodTypeId = prodTypeId;
    }

    public String getProdTypeName() {
        return prodTypeName;
    }

    public void setProdTypeName(String prodTypeName) {
        this.prodTypeName = prodTypeName;
    }
}
