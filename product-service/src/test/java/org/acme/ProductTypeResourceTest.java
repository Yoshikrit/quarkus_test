package org.acme;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.Response;
import org.acme.object.UpdateProductType;
import org.acme.error.ErrorMessage;
import org.acme.error.ErrorResponse;
import org.acme.object.ProductType;
import org.acme.object.ProductTypeResponse;
import org.acme.resource.ProductTypeResource;
import org.acme.service.ProductTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

@QuarkusTest
public class ProductTypeResourceTest {

    @Inject
    Validator validator;
    @InjectMock
    ProductTypeService productTypeService;
    @Inject
    ProductTypeResource productTypeResource;

    private ProductType productType;
    private List<ProductType> productTypeList;
    private UpdateProductType updateProductType;
    private ProductTypeResponse prodTypeResponse;
    private ErrorResponse errorResponse;

    @BeforeEach
    void setUp() {
        productType = new ProductType();
        productType.setProdTypeId(1);
        productType.setProdTypeName("one");

        productTypeList = new ArrayList<>();
        productTypeList.add(productType);

        updateProductType = new UpdateProductType();
        updateProductType.setProdTypeName("one");
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void createProductTypeOK() {
        prodTypeResponse = new ProductTypeResponse(productType);

        Set<ConstraintViolation<ProductType>> validateResult = validator.validate(productType);
        assertEquals(new HashSet<>(), validateResult);

        Mockito.when(productTypeService.createProductType(productType)).thenReturn(prodTypeResponse);
        Response response = productTypeResource.createProductType(productType);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ProductType entity = (ProductType) response.getEntity();
        assertEquals(1, entity.getProdTypeId());
        assertEquals("one", entity.getProdTypeName());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void createProductTypeKO() {
        errorResponse = new ErrorResponse("409", new ErrorMessage(""));
        prodTypeResponse = new ProductTypeResponse(errorResponse);

        Set<ConstraintViolation<ProductType>> validateResult = validator.validate(productType);
        assertEquals(new HashSet<>(), validateResult);

        Mockito.when(productTypeService.createProductType(productType)).thenReturn(prodTypeResponse);
        Response response = productTypeResource.createProductType(productType);
        assertNotNull(response);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ErrorResponse entity = (ErrorResponse) response.getEntity();
        assertEquals("409", entity.getErrorId());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void getProductTypeByIdOK() {
        prodTypeResponse = new ProductTypeResponse(productType);

        Mockito.when(productTypeService.findProductTypeById(1)).thenReturn(prodTypeResponse);
        Response response = productTypeResource.getProductTypeById(1);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ProductType entity = (ProductType) response.getEntity();
        assertEquals(1, entity.getProdTypeId());
        assertEquals("one", entity.getProdTypeName());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void getProductTypeByIdKO() {
        errorResponse = new ErrorResponse("404", new ErrorMessage(""));
        prodTypeResponse = new ProductTypeResponse(errorResponse);

        Mockito.when(productTypeService.findProductTypeById(1)).thenReturn(prodTypeResponse);
        Response response = productTypeResource.getProductTypeById(1);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ErrorResponse entity = (ErrorResponse) response.getEntity();
        assertEquals("404", entity.getErrorId());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void getAllProductTypes() {
        prodTypeResponse = new ProductTypeResponse(productTypeList);

        Mockito.when(productTypeService.findAllProductTypes()).thenReturn(prodTypeResponse);
        Response response = productTypeResource.getAllProductTypes();
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
        prodTypeResponse = new ProductTypeResponse(productType);

        Set<ConstraintViolation<UpdateProductType>> validateResult = validator.validate(updateProductType);
        assertEquals(new HashSet<>(), validateResult);

        Mockito.when(productTypeService.updateProductTypeById(1, updateProductType)).thenReturn(prodTypeResponse);
        Response response = productTypeResource.updateProductType(1, updateProductType);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ProductType entity = (ProductType) response.getEntity();
        assertEquals(1, entity.getProdTypeId());
        assertEquals("one", entity.getProdTypeName());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void updateProductTypeKO() {
        errorResponse = new ErrorResponse("404", new ErrorMessage(""));
        prodTypeResponse = new ProductTypeResponse(errorResponse);

        Set<ConstraintViolation<UpdateProductType>> validateResult = validator.validate(updateProductType);
        assertEquals(new HashSet<>(), validateResult);

        Mockito.when(productTypeService.updateProductTypeById(1, updateProductType)).thenReturn(prodTypeResponse);
        Response response = productTypeResource.updateProductType(1, updateProductType);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ErrorResponse entity = (ErrorResponse) response.getEntity();
        assertEquals("404", entity.getErrorId());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void deleteProductTypeOK() {
        prodTypeResponse = new ProductTypeResponse(false);

        Mockito.when(productTypeService.deleteProductType(1)).thenReturn(prodTypeResponse);
        Response response = productTypeResource.deleteProductType(1);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNull(response.getEntity());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void deleteProductTypeKO() {
        errorResponse = new ErrorResponse("404", new ErrorMessage(""));
        prodTypeResponse = new ProductTypeResponse(errorResponse);

        Mockito.when(productTypeService.deleteProductType(1)).thenReturn(prodTypeResponse);
        Response response = productTypeResource.deleteProductType(1);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ErrorResponse entity = (ErrorResponse) response.getEntity();
        assertEquals("404", entity.getErrorId());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void createProductTypeValidationError() {
        ProductType productTypeNameNull = new ProductType(1, "");

        Set<ConstraintViolation<ProductType>> validateResult = validator.validate(productTypeNameNull);
        assertNotNull(validateResult);

        Response response = productTypeResource.createProductType(productTypeNameNull);
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ErrorResponse entity = (ErrorResponse) response.getEntity();
        assertEquals("400", entity.getErrorId());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Manager"})
    void updateProductTypeValidationError() {
        UpdateProductType UpdateProductTypeNameNull = new UpdateProductType("");

        Set<ConstraintViolation<UpdateProductType>> validateResult = validator.validate(UpdateProductTypeNameNull);
        assertNotNull(validateResult);

        Response response = productTypeResource.updateProductType(1, UpdateProductTypeNameNull);
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        ErrorResponse entity = (ErrorResponse) response.getEntity();
        assertEquals("400", entity.getErrorId());
    }
}
