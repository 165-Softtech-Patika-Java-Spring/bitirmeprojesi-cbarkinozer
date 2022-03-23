package com.softtech.graduationproject.app.prd.service;

import com.softtech.graduationproject.app.prd.entity.PrdProduct;
import com.softtech.graduationproject.app.prd.service.entityservice.PrdProductEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class PrdProductUtilityService {

    private final PrdProductEntityService prdProductEntityService;
    private final PrdProductValidationService prdProductValidationService;

    public BigDecimal calculatePriceWithControl(PrdProduct prdProduct){

        Long vrtVatRateId = prdProduct.getVrtVatRateId();

        Integer vatRateInt = prdProductEntityService.getVatRateByVatRateId(vrtVatRateId);

        Double vatRate = Double.valueOf(vatRateInt);

        BigDecimal vatFreePrice = prdProduct.getVatFreePrice();

        prdProductValidationService.controlIsPriceNull(vatFreePrice);

        BigDecimal price = vatFreePrice.add(vatFreePrice.multiply(BigDecimal.valueOf(vatRate/100)));

        prdProductValidationService.controlIsPricePositive(price);

        return price;
    }
}
