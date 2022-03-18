package com.softtech.graduationproject.app.prd.controller;

import com.softtech.graduationproject.app.gen.dto.RestResponse;
import com.softtech.graduationproject.app.prd.dto.PrdProductAnalysisRequestDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductSaveRequestDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductUpdateRequestDto;
import com.softtech.graduationproject.app.prd.service.PrdProductService;
import com.softtech.graduationproject.app.vrt.enums.VrtProductType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class PrdProductController {

    private final PrdProductService prdProductService;


    @Operation(
            tags="Product Controller",
            summary = "Get all products",
            description = "Gets all products."
    )

    @GetMapping
    public ResponseEntity<RestResponse<List<PrdProductDto>>> findAll(){

        List<PrdProductDto> prdProductDtoList = prdProductService.findAll();

        return ResponseEntity.ok(RestResponse.of(prdProductDtoList));
    }


    @Operation(
            tags = "Product Controller",
            summary = "Get a product",
            description = "Gets a product by id."
    )
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<PrdProductDto>> findById(@PathVariable Long id){

        PrdProductDto prdProductDto = prdProductService.findById(id);

        return ResponseEntity.ok(RestResponse.of(prdProductDto));
    }


    @Operation(
            tags = "Product Controller",
            summary = "Get products by price interval",
            description = "Gets products in the range by given min and max."
    )
    @GetMapping("/find-by-price-interval")
    public ResponseEntity<RestResponse<List<PrdProductDto>>> findByPriceInterval(@RequestParam BigDecimal min,
                                                                                 @RequestParam BigDecimal max){

        List<PrdProductDto> prdProductDtoList = prdProductService.findByPriceInterval(min,max);

        return ResponseEntity.ok(RestResponse.of(prdProductDtoList));
    }

    @Operation(
            tags = "Product Controller",
            summary = "Get products by product type",
            description = "Gets all products by product type."
    )
    @GetMapping("/find-by-product-type")
    public ResponseEntity<RestResponse<List<PrdProductDto>>> findByProductType(@RequestParam VrtProductType vrtProductType){

        List<PrdProductDto> prdProductDtoList = prdProductService.findByProductType(vrtProductType);

        return ResponseEntity.ok(RestResponse.of(prdProductDtoList));
    }


    @Operation(
            tags = "Product Controller",
            summary = "Get an analysis about products.",
            description = "Gets an analysis about product's minimum, maximum, and average prices, " +
                    "count of products and VAT rate by product type."
    )
    @GetMapping("/get-product-analysis")
    public ResponseEntity<RestResponse<PrdProductAnalysisRequestDto>> getProductAnalysis(){

        PrdProductAnalysisRequestDto prdProductAnalysisRequestDto = prdProductService.getProductAnalysis();

        return ResponseEntity.ok(RestResponse.of(prdProductAnalysisRequestDto));
    }


    @Operation(
            tags="Product Controller",
            summary = "Save a product",
            description = "Saves a new product"
    )
    @PostMapping("/save")
    public ResponseEntity<RestResponse<MappingJacksonValue>> save(@RequestBody PrdProductSaveRequestDto prdProductSaveRequestDto){

        PrdProductDto prdProductDto = prdProductService.save(prdProductSaveRequestDto);

        MappingJacksonValue mappingJacksonValue = createLinksForSave(prdProductDto);

        return ResponseEntity.ok(RestResponse.of(mappingJacksonValue));
    }

    private MappingJacksonValue createLinksForSave (PrdProductDto prdProductDto){

        WebMvcLinkBuilder linkGet = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).findById(prdProductDto.getId()));

        WebMvcLinkBuilder linkDelete = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).delete(prdProductDto.getId()));

        EntityModel<PrdProductDto> entityModel = EntityModel.of(prdProductDto);

        entityModel.add(linkGet.withRel("find-by-id"));
        entityModel.add(linkDelete.withRel("delete"));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(entityModel);

        return mappingJacksonValue;
    }


    @Operation(
            tags="Product Controller",
            summary = "Update a product",
            description = "Updates a user's name and VAT-free price by id."
    )
    @PutMapping("/update")
    public ResponseEntity<RestResponse<PrdProductDto>> update(PrdProductUpdateRequestDto prdProductUpdateRequestDto){

        PrdProductDto prdProductDto = prdProductService.update(prdProductUpdateRequestDto);

        return ResponseEntity.ok(RestResponse.of(prdProductDto));
    }


    @Operation(
            tags="Product Controller",
            summary = "Delete a Product",
            description = "Deletes a Product."
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RestResponse<?>> delete(@PathVariable Long id){

        prdProductService.delete(id);

        return ResponseEntity.ok(RestResponse.empty());
    }


}
