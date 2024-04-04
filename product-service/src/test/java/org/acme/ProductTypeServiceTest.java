package org.acme;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.object.UpdateProductType;
import org.acme.entity.ProductTypeEntity;
import org.acme.error.ErrorResponse;
import org.acme.object.ProductType;
import org.acme.object.ProductTypeResponse;
import org.acme.repository.ProductTypeRepository;
import org.acme.service.ProductTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class ProductTypeServiceTest {

    @InjectMock
    ProductTypeRepository productTypeRepository;
    @Inject
    ProductTypeService productTypeService;

    private ProductType productType;
    private ProductTypeEntity productTypeEntity;
    private List<ProductTypeEntity> productTypeEntityList;
    private List<ProductType> productTypeList;
    private UpdateProductType updateProductType;
    private ProductTypeResponse prodTypeResponse;
    private ErrorResponse errorResponse;

    @BeforeEach
    void setUp() {
        productType = new ProductType(1, "one");

        productTypeEntity = new ProductTypeEntity(1, "one");

        productTypeEntityList = new ArrayList<>();
        productTypeEntityList.add(productTypeEntity);

        productTypeList = new ArrayList<>();
        productTypeList.add(productType);

        updateProductType = new UpdateProductType("one");
    }

    @Test
    void createProductTypeOK() {
        ProductTypeEntity nullProdType = null;
        Mockito.when(productTypeRepository.findById(1)).thenReturn(nullProdType);
        assertNull(nullProdType);
        assertEquals(null, nullProdType);

        Mockito.doNothing().when(productTypeRepository).persist(ArgumentMatchers.any(ProductTypeEntity.class));
        Mockito.when(productTypeRepository.isPersistent(any(ProductTypeEntity.class))).thenReturn(true);

        prodTypeResponse = productTypeService.createProductType(productType);
        assertNotNull(prodTypeResponse);
        assertFalse(prodTypeResponse.getError());
        assertNotNull(prodTypeResponse.getProductType());
        ProductType productTypeResult = prodTypeResponse.getProductType();
        assertEquals(1, productTypeResult.getProdTypeId());
        assertEquals("one", productTypeResult.getProdTypeName());
    }

    @Test
    void createProductTypeKO1() {
        Mockito.when(productTypeRepository.findById(1)).thenReturn(productTypeEntity);
        assertNotNull(productTypeEntity);
        assertEquals(1, productTypeEntity.getProdTypeId());
        assertEquals("one", productTypeEntity.getProdTypeName());

        prodTypeResponse = productTypeService.createProductType(productType);
        assertNotNull(prodTypeResponse);
        assertTrue(prodTypeResponse.getError());
        errorResponse = (ErrorResponse) prodTypeResponse.getErrorResponse();
        assertNotNull(errorResponse);
        assertEquals("409", errorResponse.getErrorId());
    }

    @Test
    void createProductTypeKO2() {
        ProductTypeEntity nullProdType = null;
        Mockito.when(productTypeRepository.findById(1)).thenReturn(nullProdType);
        assertNull(nullProdType);
        assertEquals(null, nullProdType);

        Mockito.doNothing().when(productTypeRepository).persist(ArgumentMatchers.any(ProductTypeEntity.class));
        Mockito.when(productTypeRepository.isPersistent(any(ProductTypeEntity.class))).thenReturn(false);

        prodTypeResponse = productTypeService.createProductType(productType);
        assertNotNull(prodTypeResponse);
        assertTrue(prodTypeResponse.getError());
        errorResponse = (ErrorResponse) prodTypeResponse.getErrorResponse();
        assertNotNull(errorResponse);
        assertEquals("500", errorResponse.getErrorId());
    }

    @Test
    void findProductTypeByIdOK() {
        Mockito.when(productTypeRepository.findById(1)).thenReturn(productTypeEntity);

        prodTypeResponse = productTypeService.findProductTypeById(1);
        assertNotNull(prodTypeResponse);
        assertFalse(prodTypeResponse.getError());
        assertNotNull(prodTypeResponse.getProductType());
        ProductType productTypeResult = prodTypeResponse.getProductType();
        assertEquals(1, productTypeResult.getProdTypeId());
        assertEquals("one", productTypeResult.getProdTypeName());
    }

    @Test
    void findProductTypeByIdKO() {
        ProductTypeEntity nullProdType = null;
        Mockito.when(productTypeRepository.findById(1)).thenReturn(nullProdType);
        assertNull(nullProdType);

        prodTypeResponse = productTypeService.findProductTypeById(1);
        assertNotNull(prodTypeResponse);
        assertTrue(prodTypeResponse.getError());
        errorResponse = (ErrorResponse) prodTypeResponse.getErrorResponse();
        assertNotNull(errorResponse);
        assertEquals("404", errorResponse.getErrorId());
    }

    @Test
    void findAllProductTypesOK() {
        Mockito.when(productTypeRepository.listAll()).thenReturn(productTypeEntityList);

        prodTypeResponse = productTypeService.findAllProductTypes();
        assertNotNull(prodTypeResponse);
        assertFalse(prodTypeResponse.getError());
        assertNotNull(prodTypeResponse.getProductTypeList());
        List<ProductType> productTypeListResult = prodTypeResponse.getProductTypeList();
        ProductType productTypeResult = productTypeListResult.get(0);
        assertEquals(1, productTypeResult.getProdTypeId());
        assertEquals("one", productTypeResult.getProdTypeName());
    }

    @Test
    void updateProductTypeByIdOK() {
        Mockito.when(productTypeRepository.findById(1)).thenReturn(productTypeEntity);
        assertNotNull(productTypeEntity);
        assertEquals(1, productTypeEntity.getProdTypeId());
        assertEquals("one", productTypeEntity.getProdTypeName());

        prodTypeResponse = productTypeService.updateProductTypeById(1, updateProductType);
        assertNotNull(prodTypeResponse);
        assertFalse(prodTypeResponse.getError());
        assertNotNull(prodTypeResponse.getProductType());
        ProductType productTypeResult = prodTypeResponse.getProductType();
        assertEquals(1, productTypeResult.getProdTypeId());
        assertEquals("one", productTypeResult.getProdTypeName());
    }

    @Test
    void updateProductTypeByIdKO() {
        ProductTypeEntity nullProdType = null;
        Mockito.when(productTypeRepository.findById(1)).thenReturn(nullProdType);
        assertNull(nullProdType);
        assertEquals(null, nullProdType);

        prodTypeResponse = productTypeService.updateProductTypeById(1, updateProductType);
        assertNotNull(prodTypeResponse);
        assertTrue(prodTypeResponse.getError());
        errorResponse = (ErrorResponse) prodTypeResponse.getErrorResponse();
        assertNotNull(errorResponse);
        assertEquals("404", errorResponse.getErrorId());
    }

    @Test
    void deleteProductTypeOK() {
        Mockito.when(productTypeRepository.findById(1)).thenReturn(productTypeEntity);
        assertNotNull(productTypeEntity);
        Mockito.when(productTypeRepository.deleteById(1)).thenReturn(true);

        prodTypeResponse = productTypeService.deleteProductType(1);
        assertNotNull(prodTypeResponse);
        assertFalse(prodTypeResponse.getError());
    }

    @Test
    void deleteProductTypeKO1() {
        ProductTypeEntity nullProdType = null;
        Mockito.when(productTypeRepository.findById(1)).thenReturn(nullProdType);
        assertNull(nullProdType);

        prodTypeResponse = productTypeService.deleteProductType(1);
        assertNotNull(prodTypeResponse);
        assertEquals(true, prodTypeResponse.getError());
        errorResponse = (ErrorResponse) prodTypeResponse.getErrorResponse();
        assertNotNull(errorResponse);
        assertEquals("404", errorResponse.getErrorId());
    }

    @Test
    void deleteProductTypeKO2() {
        Mockito.when(productTypeRepository.findById(1)).thenReturn(productTypeEntity);
        assertNotNull(productTypeEntity);
        Mockito.when(productTypeRepository.deleteById(1)).thenReturn(false);

        prodTypeResponse = productTypeService.deleteProductType(1);
        assertNotNull(prodTypeResponse);
        assertTrue(prodTypeResponse.getError());
        errorResponse = (ErrorResponse) prodTypeResponse.getErrorResponse();
        assertNotNull(errorResponse);
        assertEquals("500", errorResponse.getErrorId());
    }
}
