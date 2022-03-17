package com.softtech.graduationproject.app.vrt.service;

import com.softtech.graduationproject.app.gen.entity.BaseEntity;
import com.softtech.graduationproject.app.gen.exceptions.IllegalFieldException;
import com.softtech.graduationproject.app.gen.exceptions.ItemAlreadyExistsException;
import com.softtech.graduationproject.app.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.app.vrt.entity.VrtVatRate;
import com.softtech.graduationproject.app.vrt.enums.VrtErrorMessage;
import com.softtech.graduationproject.app.vrt.enums.VrtProductType;
import com.softtech.graduationproject.app.vrt.service.entityservice.VrtVatRateEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VrtVatRateValidationService {

    private final VrtVatRateEntityService vrtVatRateEntityService;

    public void controlIsVrtVatRateExist(Long id){

        boolean isExist = vrtVatRateEntityService.existsById(id);
        if (!isExist){
            throw new ItemNotFoundException(VrtErrorMessage.VAT_RATE_NOT_FOUND);
        }

    }

    public void controlIsVatRateNegative(VrtVatRate vrtVatRate) {

        if(vrtVatRate.getVatRate()<0){
            throw new IllegalFieldException(VrtErrorMessage.VAT_RATE_CANNOT_BE_NEGATIVE);
        }

    }

    public void controlIsProductTypeUnique(VrtVatRate vrtVatRate){

        VrtProductType vrtProductType = vrtVatRate.getProductType();

        Optional<VrtVatRate> vrtVatRateOptional = vrtVatRateEntityService.findByProductType(vrtProductType);
        boolean didMatchedItself = didMatchedItself(vrtVatRateOptional, vrtVatRate);

        if(!didMatchedItself){
            throw new ItemAlreadyExistsException(VrtErrorMessage.VAT_RATE_ALREADY_EXIST);
        }

    }

    private Boolean didMatchedItself(Optional<VrtVatRate> optional, VrtVatRate ownEntity){

        VrtVatRate vrtVatRate;

        if(optional.isPresent()){

            vrtVatRate = optional.get();
            return vrtVatRate.getId().equals(ownEntity.getId());

        }else{return true;}
    }


}
