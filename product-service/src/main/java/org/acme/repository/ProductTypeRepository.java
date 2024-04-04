package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.ProductTypeEntity;

@ApplicationScoped
public class ProductTypeRepository implements PanacheRepositoryBase<ProductTypeEntity, Integer> {
}
