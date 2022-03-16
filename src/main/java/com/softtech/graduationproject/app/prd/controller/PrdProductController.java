package com.softtech.graduationproject.app.prd.controller;

import com.softtech.graduationproject.app.gen.dto.RestResponse;
import com.softtech.graduationproject.app.prd.dto.PrdProductDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductSaveRequestDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductUpdateRequestDto;
import com.softtech.graduationproject.app.prd.service.PrdProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            summary = "List products by price interval",
            description = "Lists products in the range by given min and max."
    )
    @GetMapping("/list-by-price-interval")
    public ResponseEntity<RestResponse<List<PrdProductDto>>> listByPriceInterval(@RequestParam BigDecimal min,
                                                                                 @RequestParam BigDecimal max){

        List<PrdProductDto> prdProductDtoList = prdProductService.listByPriceInterval(min,max);

        return ResponseEntity.ok(RestResponse.of(prdProductDtoList));
    }

    @Operation(
            tags="Product Controller",
            summary = "Save a product",
            description = "Saves a new product"
    )
    @PostMapping("/save")
    public ResponseEntity<RestResponse<PrdProductDto>> save(@RequestBody PrdProductSaveRequestDto prdProductSaveRequestDto){

        PrdProductDto prdProductDto = prdProductService.save(prdProductSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(prdProductDto));
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


    /*
findAllByProductType
findDetailsByProductType (query)
    * */
}
