package org.acme.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.object.UpdateProductType;
import org.acme.object.ProductType;
import org.acme.error.ErrorResponse;
import org.acme.error.ErrorMessage;
import org.acme.object.ProductTypeResponse;
import org.acme.service.ProductTypeService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Path("producttype")
@Tag(name = "ProductType", description = "Operation about getting ProductType")
public class ProductTypeResource {

    @Inject
    Validator validator;

    @Inject
    ProductTypeService productTypeService;

    @POST
    @Path("/")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Manager", "Employee"})
    @Operation(
            operationId = "Create ProductType",
            summary = "Create product type",
            description = "Create product type"
    )
    public Response createProductType(ProductType productType){
        Set<ConstraintViolation<ProductType>> validate = validator.validate(productType);
        if(validate.isEmpty()) {
            ProductTypeResponse result = productTypeService.createProductType(productType);
            if(result.getError()) {
                return Response.status(Integer.parseInt(result.getErrorResponse().getErrorId()))
                        .entity(result.getErrorResponse())
                        .build();
            }
            return Response.status(Response.Status.CREATED)
                    .entity(result.getProductType())
                    .build();
        }
        else {
            Set<String> uniqueFields = new HashSet<>();
            ErrorResponse errorResponse = new ErrorResponse(
                "400", validate.stream()
                .filter(violation -> uniqueFields.add(violation.getPropertyPath().toString()))
                .map(violation -> new ErrorMessage(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList())
            );
            return Response.status(Integer.parseInt(errorResponse.getErrorId()))
                    .entity(errorResponse)
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Manager", "Employee"})
    @Operation(
            operationId = "Get ProductType By ID",
            summary = "Get product type by id",
            description = "Get product type by id"
    )
    public Response getProductTypeById(@PathParam("id") int id){
        ProductTypeResponse result = productTypeService.findProductTypeById(id);
        if(result.getError()) {
            return Response.status(Integer.parseInt(result.getErrorResponse().getErrorId()))
                    .entity(result.getErrorResponse())
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(result.getProductType())
                .build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Manager", "Employee"})
    @Operation(
            operationId = "Get All ProductType",
            summary = "Get all product type",
            description = "Get all product type"
    )
    public Response getAllProductTypes(){
        ProductTypeResponse result = productTypeService.findAllProductTypes();
        return Response.status(Response.Status.OK)
                .entity(result.getProductTypeList())
                .build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Manager", "Employee"})
    @Operation(
            operationId = "Update ProductType By ID",
            summary = "Update product type by id",
            description = "Update product type by id"
    )
    public Response updateProductType(@PathParam("id") int id, UpdateProductType updateProductType){
        Set<ConstraintViolation<UpdateProductType>> validate = validator.validate(updateProductType);
        if(validate.isEmpty()) {
            ProductTypeResponse result = productTypeService.updateProductTypeById(id, updateProductType);
            if(result.getError()) {
                return Response.status(Integer.parseInt(result.getErrorResponse().getErrorId()))
                        .entity(result.getErrorResponse())
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity(result.getProductType())
                    .build();
        }
        else {
            Set<String> uniqueFields = new HashSet<>();
            ErrorResponse errorResponse = new ErrorResponse(
                    "400", validate.stream()
                    .filter(violation -> uniqueFields.add(violation.getPropertyPath().toString()))
                    .map(violation -> new ErrorMessage(violation.getPropertyPath().toString(), violation.getMessage()))
                    .collect(Collectors.toList())
            );
            return Response.status(Integer.parseInt(errorResponse.getErrorId()))
                    .entity(errorResponse)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Manager", "Employee"})
    @Operation(
            operationId = "Delete ProductType By ID",
            summary = "Delete product type by id",
            description = "Delete product type by id"
    )
    public Response deleteProductType(@PathParam("id") int id){
        ProductTypeResponse result = productTypeService.deleteProductType(id);
        if(result.getError()) {
            return Response.status(Integer.parseInt(result.getErrorResponse().getErrorId()))
                    .entity(result.getErrorResponse())
                    .build();
        }
        return Response.status(Response.Status.OK)
                .build();
    }
}
