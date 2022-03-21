package com.softtech.graduationproject.app.prd.service;

import com.softtech.graduationproject.app.gen.exceptions.IllegalFieldException;
import com.softtech.graduationproject.app.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.app.prd.dto.PrdVatRateDto;
import com.softtech.graduationproject.app.prd.entity.PrdProduct;
import com.softtech.graduationproject.app.prd.enums.PrdErrorMessage;
import com.softtech.graduationproject.app.prd.service.entityservice.PrdProductEntityService;
import com.softtech.graduationproject.app.vrt.enums.VrtErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class PrdProductValidationService {

    private final PrdProductEntityService prdProductEntityService;

    public void controlIsPrdProductExist(Long id){

        boolean isExist = prdProductEntityService.existsById(id);
        if (!isExist){
            throw new ItemNotFoundException(PrdErrorMessage.PRODUCT_NOT_FOUND);
        }

    }


    public void controlAreFieldsNonNull(PrdProduct prdProduct) {

        boolean hasNullField = prdProduct.getUsrUserId() == null    ||
                          prdProduct.getVrtVatRateId() == null ||
                          prdProduct.getName().isBlank()       ||
                          prdProduct.getVatFreePrice() == null;

        if(hasNullField){

            throw new IllegalFieldException(PrdErrorMessage.FIELD_CANNOT_BE_NULL);
        }
    }


    public void controlIsPricePositive(PrdProduct prdProduct){

        if(prdProduct.getVatFreePrice().compareTo(BigDecimal.ZERO) < 1 ){

            throw new IllegalFieldException(PrdErrorMessage.PRICE_MUST_BE_POSITIVE);
        }
    }

    public void controlIsPricePositive(BigDecimal price){

        if(price.compareTo(BigDecimal.ZERO) < 1 ){

            throw new IllegalFieldException(PrdErrorMessage.PRICE_MUST_BE_POSITIVE);
        }
    }

    public void controlIsPriceNull(BigDecimal price) {

        if(price == null){
            throw new IllegalFieldException(PrdErrorMessage.PRICE_CANNOT_BE_NULL);
        }

    }


    public void controlDoesVatRateExist(PrdVatRateDto prdVatRateDto){

        if(prdVatRateDto.getVatRate() ==null) {

            throw new ItemNotFoundException(VrtErrorMessage.VAT_RATE_NOT_FOUND);
        }
    }


}
