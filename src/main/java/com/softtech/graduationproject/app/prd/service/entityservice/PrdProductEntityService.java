package com.softtech.graduationproject.app.prd.service.entityservice;

import com.softtech.graduationproject.app.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.app.gen.service.BaseEntityService;
import com.softtech.graduationproject.app.prd.dao.PrdProductDao;
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


    public List<PrdProduct> listByPriceInterval(BigDecimal min, BigDecimal max) {

        List<PrdProduct> prdProductList = getDao().getAllByPriceBetween(min,max);

        return prdProductList;
    }

    public int getVatRateByVatRateId(PrdProduct prdProduct) {

        Long vatRateId = prdProduct.getVrtVatRateId();

        Optional<PrdVatRateDto> prdVatRateDtoOptional = getDao().getVatRateByVatRateId(vatRateId);

        PrdVatRateDto prdVatRateDto = controlIsVatRateExist(prdVatRateDtoOptional);

        int vatRate = prdVatRateDto.getVatRate();

        return vatRate;
    }

    private PrdVatRateDto controlIsVatRateExist(Optional<PrdVatRateDto> prdVatRateDtoOptional){

        PrdVatRateDto prdVatRateDto;

        if(prdVatRateDtoOptional.isPresent()){

            prdVatRateDto = prdVatRateDtoOptional.get();

            return prdVatRateDto;

        }else{

            throw new ItemNotFoundException(VrtErrorMessage.VAT_RATE_NOT_FOUND);
        }

    }
}
