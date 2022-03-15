package com.softtech.graduationproject.app.vrt.service.entityservice;

import com.softtech.graduationproject.app.gen.service.BaseEntityService;
import com.softtech.graduationproject.app.vrt.dao.VrtVatRateDao;
import com.softtech.graduationproject.app.vrt.entity.VrtVatRate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VrtVatRateEntityService extends BaseEntityService<VrtVatRate, VrtVatRateDao> {

    public VrtVatRateEntityService(VrtVatRateDao vrtVatRateDao){

        super(vrtVatRateDao);
    }


}
