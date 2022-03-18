package com.softtech.graduationproject.app.prd.service.entityservice;

import com.softtech.graduationproject.app.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.app.gen.service.BaseEntityService;
import com.softtech.graduationproject.app.prd.dao.PrdProductDao;
import com.softtech.graduationproject.app.prd.dto.PrdProductAnalysisRequestDto;
import com.softtech.graduationproject.app.prd.dto.PrdVatRateDto;
import com.softtech.graduationproject.app.prd.entity.PrdProduct;
import com.softtech.graduationproject.app.vrt.enums.VrtErrorMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrdProductEntityService extends BaseEntityService<PrdProduct,PrdProductDao> {


    public PrdProductEntityService(PrdProductDao prdProductDao){

        super(prdProductDao);
    }


    public List<PrdProduct> findByPriceInterval(BigDecimal min, BigDecimal max) {

        List<PrdProduct> prdProductList = getDao().findAllByPriceBetween(min,max);

        return prdProductList;
    }


    public List<PrdProduct> findByVatRateId(Long vrtVatRateId) {

        List<PrdProduct> prdProductList = getDao().findAllByVrtVatRateId(vrtVatRateId);

        return prdProductList;
    }


    public Integer getVatRateByVatRateId(PrdProduct prdProduct) {

        Long vatRateId = prdProduct.getVrtVatRateId();

        PrdVatRateDto prdVatRateDto = getDao().getVatRateByVatRateId(vatRateId);

        controlIsVatRateExist(prdVatRateDto);

        Integer vatRate = prdVatRateDto.getVatRate();

        return vatRate;
    }


    private void controlIsVatRateExist(PrdVatRateDto prdVatRateDto){

        if(prdVatRateDto.getVatRate() ==null) {

            throw new ItemNotFoundException(VrtErrorMessage.VAT_RATE_NOT_FOUND);
        }
    }


    public PrdProductAnalysisRequestDto getProductAnalysis() {

        PrdProductAnalysisRequestDto prdProductAnalysisRequestDto = getDao().getProductAnalysis();

        return prdProductAnalysisRequestDto;
    }


}
