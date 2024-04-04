package org.acme;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.Response;
import org.acme.entity.ProductTypeEntity;
import org.acme.error.ErrorResponse;
import org.acme.object.ProductType;
import org.acme.object.ProductTypeResponse;
import org.acme.object.UpdateProductType;
import org.acme.repository.ProductTypeRepository;
import org.acme.resource.ProductTypeResource;
import org.acme.service.ProductTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class ProductTypeTestIT {
    @InjectMock
    ProductTypeRepository productTypeRepository;
    @Inject
    ProductTypeService productTypeService;
    @Inject
    ProductTypeResource productTypeResource;
    @Inject
    Validator validator;

    private ProductType productType;
    private ProductTypeEntity productTypeEntity;
    private List<ProductType> productTypeList;
    private List<ProductTypeEntity> productTypeEntityList;
    private UpdateProductType updateProductType;
    private ProductTypeResponse prodTypeResponse;
    private ErrorResponse errorResponse;
    private Response response;

    @BeforeEach
    void setUp() {
        productTypeEntity = new ProductTypeEntity(1, "one");

        productType = new ProductType();
        productType.setProdTypeId(1);
        productType.setProdTypeName("one");

        productTypeList = new ArrayList<>();
        productTypeList.add(productType);

        productTypeEntityList = new ArrayList<>();
        productTypeEntityList.add(productTypeEntity);

        updateProductType = new UpdateProductType();
        updateProductType.setProdTypeName("one");

        productTypeEntity = new ProductTypeEntity(1, "one");
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void createProductTypeOK() {
        Set<ConstraintViolation<ProductType>> validateResult = validator.validate(productType);
        assertEquals(new HashSet<>(), validateResult);

        ProductTypeEntity nullProdType = null;
        Mockito.when(productTypeRepository.findById(1)).thenReturn(nullProdType);
        Mockito.doNothing().when(productTypeRepository).persist(ArgumentMatchers.any(ProductTypeEntity.class));
        Mockito.when(productTypeRepository.isPersistent(any(ProductTypeEntity.class))).thenReturn(true);

        prodTypeResponse = productTypeService.createProductType(productType);
        response = productTypeResource.createProductType(productType);

        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ProductType entity = (ProductType) response.getEntity();
        assertEquals(1, entity.getProdTypeId());
        assertEquals("one", entity.getProdTypeName());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void getProductTypeByIdOK() {
        Mockito.when(productTypeRepository.findById(1)).thenReturn(productTypeEntity);

        prodTypeResponse = productTypeService.findProductTypeById(1);
        response = productTypeResource.getProductTypeById(1);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ProductType entity = (ProductType) response.getEntity();
        assertEquals(1, entity.getProdTypeId());
        assertEquals("one", entity.getProdTypeName());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void getAllProductTypes() {
        Mockito.when(productTypeRepository.listAll()).thenReturn(productTypeEntityList);

        prodTypeResponse = productTypeService.findAllProductTypes();
        response = productTypeResource.getAllProductTypes();

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());

        List<ProductType> entityList = (List<ProductType>) response.getEntity();
        ProductType entity1 = entityList.get(0);

        assertEquals(1, entity1.getProdTypeId());
        assertEquals("one", entity1.getProdTypeName());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void updateProductTypeOK() {
        Set<ConstraintViolation<UpdateProductType>> validateResult = validator.validate(updateProductType);
        assertEquals(new HashSet<>(), validateResult);

        Mockito.when(productTypeRepository.findById(1)).thenReturn(productTypeEntity);

        prodTypeResponse = productTypeService.updateProductTypeById(1, updateProductType);
        response = productTypeResource.updateProductType(1, updateProductType);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ProductType entity = (ProductType) response.getEntity();
        assertEquals(1, entity.getProdTypeId());
        assertEquals("one", entity.getProdTypeName());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void deleteProductTypeOK() {
        prodTypeResponse = new ProductTypeResponse(false);

        Mockito.when(productTypeRepository.findById(1)).thenReturn(productTypeEntity);
        Mockito.when(productTypeRepository.deleteById(1)).thenReturn(true);

        prodTypeResponse = productTypeService.deleteProductType(1);
        Response response = productTypeResource.deleteProductType(1);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNull(response.getEntity());
    }
}
