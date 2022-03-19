package com.softtech.graduationproject.app.prd.service;

import com.softtech.graduationproject.app.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.app.prd.converter.PrdProductMapper;
import com.softtech.graduationproject.app.prd.dto.PrdProductAnalysisRequestDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductSaveRequestDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductUpdateRequestDto;
import com.softtech.graduationproject.app.prd.entity.PrdProduct;
import com.softtech.graduationproject.app.prd.service.entityservice.PrdProductEntityService;
import com.softtech.graduationproject.app.vrt.entity.VrtVatRate;
import com.softtech.graduationproject.app.vrt.enums.VrtErrorMessage;
import com.softtech.graduationproject.app.vrt.enums.VrtProductType;
import com.softtech.graduationproject.app.vrt.service.entityservice.VrtVatRateEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PrdProductService {

    private final PrdProductEntityService prdProductEntityService;
    private final VrtVatRateEntityService vrtVatRateEntityService;

    private final PrdProductValidationService prdProductValidationService;


    public List<PrdProductDto> findAllProducts() {

        List<PrdProduct> prdProductList = prdProductEntityService.findAll();

        List<PrdProductDto> prdProductDtoList = PrdProductMapper.INSTANCE.convertToPrdProductDtoList(prdProductList);

        return prdProductDtoList;
    }


    public PrdProductDto findProductById(Long id) {

        PrdProduct prdProduct = prdProductEntityService.getByIdWithControl(id);

        PrdProductDto prdProductDto = PrdProductMapper.INSTANCE.convertToPrdProductDto(prdProduct);

        return prdProductDto;
    }


    public List<PrdProductDto> findProductsByPriceInterval(BigDecimal min, BigDecimal max) {

        List<PrdProduct> prdProductList = prdProductEntityService.findProductsByPriceInterval(min,max);

        List<PrdProductDto> prdProductDtoList = PrdProductMapper.INSTANCE.convertToPrdProductDtoList(prdProductList);

        return prdProductDtoList;

    }


    public List<PrdProductDto> findProductsByProductType(VrtProductType vrtProductType) {

         VrtVatRate vrtVatRate = vrtVatRateEntityService.findProductsByProductType(vrtProductType)
                 .orElseThrow(()-> new ItemNotFoundException(VrtErrorMessage.VAT_RATE_NOT_FOUND));

         Long vrtVatRateId = vrtVatRate.getId();

         List<PrdProduct> prdProductList = prdProductEntityService.findProductsByVatRateId(vrtVatRateId);

         List<PrdProductDto> prdProductDtoList = PrdProductMapper.INSTANCE.convertToPrdProductDtoList(prdProductList);

         return prdProductDtoList;

    }


    public PrdProductAnalysisRequestDto getProductAnalysis() {

        PrdProductAnalysisRequestDto prdProductAnalysisRequestDto = prdProductEntityService.getProductAnalysis();

        return prdProductAnalysisRequestDto;
    }


    public PrdProductDto saveProduct(PrdProductSaveRequestDto prdProductSaveRequestDto) {

        PrdProduct prdProduct = PrdProductMapper.INSTANCE.convertToPrdProduct(prdProductSaveRequestDto);

        BigDecimal price = calculatePrice(prdProduct);
        prdProduct.setPrice(price);

        prdProductValidationService.controlAreFieldsNonNull(prdProduct);
        prdProductValidationService.controlIsPricePositive(prdProduct);

        prdProduct = prdProductEntityService.save(prdProduct);

        PrdProductDto prdProductDto = PrdProductMapper.INSTANCE.convertToPrdProductDto(prdProduct);

        return prdProductDto;
    }


    public PrdProductDto updateProduct(PrdProductUpdateRequestDto prdProductUpdateRequestDto) {

        Long id = prdProductUpdateRequestDto.getId();
        prdProductValidationService.controlIsPrdProductExist(id);

        PrdProduct prdProduct = PrdProductMapper.INSTANCE.convertToPrdProduct(prdProductUpdateRequestDto);

        BigDecimal price = calculatePrice(prdProduct);
        prdProduct.setPrice(price);

        prdProductValidationService.controlAreFieldsNonNull(prdProduct);
        prdProductValidationService.controlIsPricePositive(prdProduct);

        prdProduct = prdProductEntityService.save(prdProduct);

        PrdProductDto prdProductDto = PrdProductMapper.INSTANCE.convertToPrdProductDto(prdProduct);

        return prdProductDto;
    }

    private BigDecimal calculatePrice(PrdProduct prdProduct){

        double vatRate = prdProductEntityService.getVatRateByVatRateId(prdProduct);

        BigDecimal vatFreePrice = prdProduct.getVatFreePrice();
        BigDecimal price;

        price = vatFreePrice.add(vatFreePrice.multiply(BigDecimal.valueOf(vatRate/100)));

        return price;
    }


    public void deleteProduct(Long id) {

        PrdProduct prdProduct = prdProductEntityService.getByIdWithControl(id);

        prdProductEntityService.delete(prdProduct);

    }



}
