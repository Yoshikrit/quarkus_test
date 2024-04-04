package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "producttype")
public class ProductTypeEntity extends PanacheEntityBase {
    @Id
    @Column(name = "prodtype_code")
    @NotNull
    private int prodTypeId;

    @Column(name = "prodtype_name")
    @NotNull
    private String prodTypeName;

    public ProductTypeEntity(int prodTypeId,
                             String prodTypeName) {
        this.prodTypeId = prodTypeId;
        this.prodTypeName = prodTypeName;
    }

    public ProductTypeEntity(){}

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
