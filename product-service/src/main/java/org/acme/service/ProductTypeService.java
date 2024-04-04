package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.object.UpdateProductType;
import org.acme.entity.ProductTypeEntity;
import org.acme.error.ErrorMessage;
import org.acme.error.ErrorResponse;
import org.acme.object.ProductType;
import org.acme.object.ProductTypeResponse;
import org.acme.repository.ProductTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductTypeService {
    @Inject
    ProductTypeRepository productTypeRepository;

    @Transactional
    public ProductTypeResponse createProductType(ProductType productType) {
        int id = productType.getProdTypeId();
        ProductTypeEntity existProdType = productTypeRepository.findById(id);
        if(existProdType != null) {
            return new ProductTypeResponse(new ErrorResponse("409", new ErrorMessage("Cant Create ProductType", "Id : " + id + " Is Conflict")));
        }
        ProductTypeEntity productTypeEntity = new ProductTypeEntity(productType.getProdTypeId(), productType.getProdTypeName());

        productTypeRepository.persist(productTypeEntity);
        if (productTypeRepository.isPersistent(productTypeEntity)) {
            return new ProductTypeResponse(productType);
        }
        return new ProductTypeResponse(new ErrorResponse("500", new ErrorMessage("Cant Create ProductType", "Unexpected Error")));
    }

    public ProductTypeResponse findProductTypeById(int id) {
        ProductTypeEntity productTypeFromDB = productTypeRepository.findById(id);
        if(productTypeFromDB != null) {
            ProductType productType = new ProductType(productTypeFromDB.getProdTypeId(), productTypeFromDB.getProdTypeName());
            return new ProductTypeResponse(productType);

        }
        return new ProductTypeResponse(new ErrorResponse("404", new ErrorMessage("Cant Find ProductType", "Id : " + id + " Not Found")));
    }

    public ProductTypeResponse findAllProductTypes() {
        List<ProductTypeEntity> productTypeEntityList = productTypeRepository.listAll();
        List<ProductType> productTypeList = productTypeEntityList.stream()
                .map(productTypeEntity -> new ProductType(productTypeEntity.getProdTypeId(), productTypeEntity.getProdTypeName()))
                .collect(Collectors.toList());
        return new ProductTypeResponse(productTypeList);
    }

    @Transactional
    public ProductTypeResponse updateProductTypeById(int id, UpdateProductType updatedProductType) {
        ProductTypeEntity existingProductType = productTypeRepository.findById(id);
        if(existingProductType != null) {
            existingProductType.setProdTypeName(updatedProductType.getProdTypeName());
            return new ProductTypeResponse(new ProductType(existingProductType.getProdTypeId(), existingProductType.getProdTypeName()));
        }
        return new ProductTypeResponse(new ErrorResponse("404", new ErrorMessage("Cant Find ProductType", "Id : " + id + " Not Found")));
    }

    @Transactional
    public ProductTypeResponse deleteProductType(int id) {
        ProductTypeEntity existingProductType = productTypeRepository.findById(id);
        if(existingProductType == null) {
            return new ProductTypeResponse(new ErrorResponse("404", new ErrorMessage("Cant Find ProductType", "Id : " + " Not Found")));
        }
        boolean isDeleted = productTypeRepository.deleteById(id);
        if(isDeleted){
            return new ProductTypeResponse(false);
        }
        return new ProductTypeResponse(new ErrorResponse("500", new ErrorMessage("Cant Create ProductType", "Unexpected Error")));
    }
}
