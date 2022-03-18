package com.softtech.graduationproject.app.vrt.service.entityservice;

import com.softtech.graduationproject.app.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.app.gen.service.BaseEntityService;
import com.softtech.graduationproject.app.usr.entity.UsrUser;
import com.softtech.graduationproject.app.vrt.dao.VrtVatRateDao;
import com.softtech.graduationproject.app.vrt.entity.VrtVatRate;
import com.softtech.graduationproject.app.vrt.enums.VrtErrorMessage;
import com.softtech.graduationproject.app.vrt.enums.VrtProductType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class VrtVatRateEntityService extends BaseEntityService<VrtVatRate, VrtVatRateDao> {

    public VrtVatRateEntityService(VrtVatRateDao vrtVatRateDao){

        super(vrtVatRateDao);
    }

    public VrtVatRate findByProductType(VrtProductType vrtProductType){

        Optional<VrtVatRate> optionalVrtVatRate = getDao().findByProductType(vrtProductType);

        VrtVatRate vrtVatRate;
        if(optionalVrtVatRate.isPresent()){

            vrtVatRate = optionalVrtVatRate.get();

        }else{
            throw new ItemNotFoundException(VrtErrorMessage.VAT_RATE_NOT_FOUND);
        }

        return vrtVatRate;
    }

}
