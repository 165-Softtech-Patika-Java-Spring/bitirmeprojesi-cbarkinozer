package com.softtech.graduationproject.app.prd.service;

import com.softtech.graduationproject.app.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.app.prd.converter.PrdProductMapper;
import com.softtech.graduationproject.app.prd.dto.PrdProductDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductSaveRequestDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductUpdateRequestDto;
import com.softtech.graduationproject.app.prd.entity.PrdProduct;
import com.softtech.graduationproject.app.prd.enums.PrdErrorMessage;
import com.softtech.graduationproject.app.prd.service.entityservice.PrdProductEntityService;
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
    private final PrdProductValidationService prdProductValidationService;

    public List<PrdProductDto> findAll() {

        List<PrdProduct> prdProductList = prdProductEntityService.findAll();

        List<PrdProductDto> prdProductDtoList = PrdProductMapper.INSTANCE.convertToPrdProductDtoList(prdProductList);

        return prdProductDtoList;
    }


    public PrdProductDto findById(Long id) {

        PrdProduct prdProduct = prdProductEntityService.getByIdWithControl(id);

        PrdProductDto prdProductDto = PrdProductMapper.INSTANCE.convertToPrdProductDto(prdProduct);

        return prdProductDto;
    }


    public List<PrdProductDto> listByPriceInterval(BigDecimal min, BigDecimal max) {

        List<PrdProduct> prdProductList = prdProductEntityService.listByPriceInterval(min,max);

        List<PrdProductDto> prdProductDtoList = PrdProductMapper.INSTANCE.convertToPrdProductDtoList(prdProductList);

        return prdProductDtoList;

    }


    public PrdProductDto save(PrdProductSaveRequestDto prdProductSaveRequestDto) {

        PrdProduct prdProduct = PrdProductMapper.INSTANCE.convertToPrdProduct(prdProductSaveRequestDto);

        calculateAndSetPrice(prdProduct);

        prdProductValidationService.controlAreFieldsNonNull(prdProduct);
        prdProductValidationService.controlIsPricePositive(prdProduct);

        prdProduct = prdProductEntityService.save(prdProduct);

        PrdProductDto prdProductDto = PrdProductMapper.INSTANCE.convertToPrdProductDto(prdProduct);

        return prdProductDto;
    }


    public PrdProductDto update(PrdProductUpdateRequestDto prdProductUpdateRequestDto) {

        Long id = prdProductUpdateRequestDto.getId();
        prdProductValidationService.controlIsPrdProductExist(id);

        PrdProduct prdProduct = PrdProductMapper.INSTANCE.convertToPrdProduct(prdProductUpdateRequestDto);

        calculateAndSetPrice(prdProduct);

        prdProductValidationService.controlAreFieldsNonNull(prdProduct);
        prdProductValidationService.controlIsPricePositive(prdProduct);

        prdProduct = prdProductEntityService.save(prdProduct);

        PrdProductDto prdProductDto = PrdProductMapper.INSTANCE.convertToPrdProductDto(prdProduct);

        return prdProductDto;
    }


    private void calculateAndSetPrice(PrdProduct prdProduct){

        double vatRate = (double) prdProductEntityService.getVatRateByVatRateId(prdProduct);

        BigDecimal vatFreePrice = prdProduct.getVatFreePrice();
        BigDecimal price;

        price = vatFreePrice.add(vatFreePrice.multiply(BigDecimal.valueOf(vatRate/100)));

        prdProduct.setPrice(price);
    }


    public void delete(Long id) {

        PrdProduct prdProduct = prdProductEntityService.getByIdWithControl(id);

        prdProductEntityService.delete(prdProduct);

    }


}
