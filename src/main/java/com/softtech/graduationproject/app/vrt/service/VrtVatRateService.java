package com.softtech.graduationproject.app.vrt.service;

import com.softtech.graduationproject.app.vrt.converter.VrtVatRateMapper;
import com.softtech.graduationproject.app.vrt.dto.VrtVatRateDto;
import com.softtech.graduationproject.app.vrt.dto.VrtVatRateSaveRequestDto;
import com.softtech.graduationproject.app.vrt.dto.VrtVatRateUpdateRequestDto;
import com.softtech.graduationproject.app.vrt.entity.VrtVatRate;
import com.softtech.graduationproject.app.vrt.service.entityservice.VrtVatRateEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VrtVatRateService {

    private final VrtVatRateEntityService vrtVatRateEntityService;
    private final VrtVatRateValidationService vrtVatRateValidationService;

    public List<VrtVatRateDto> findAll() {

        List<VrtVatRate> vrtVatRateList = vrtVatRateEntityService.findAll();

        List<VrtVatRateDto> VrtVatRateDtoList = VrtVatRateMapper.INSTANCE.convertToVrtVatRateDtoList(vrtVatRateList);

        return VrtVatRateDtoList;
    }


    public VrtVatRateDto save(VrtVatRateSaveRequestDto vrtVatRateSaveRequestDto) {

        VrtVatRate vrtVatRate = VrtVatRateMapper.INSTANCE.convertToVrtVatRate(vrtVatRateSaveRequestDto);

        vrtVatRateValidationService.controlIsVatRateNegative(vrtVatRate);
        vrtVatRateValidationService.controlIsProductTypeUnique(vrtVatRate);

         vrtVatRate = vrtVatRateEntityService.save(vrtVatRate);

        VrtVatRateDto  vrtVatRateDto = VrtVatRateMapper.INSTANCE.convertToVrtVatRateDto(vrtVatRate);

        return vrtVatRateDto;
    }


    public VrtVatRateDto update(VrtVatRateUpdateRequestDto vrtVatRateUpdateRequestDto) {

        Long id = vrtVatRateUpdateRequestDto.getId();

        vrtVatRateValidationService.controlIsVrtVatRateExist(id);

        VrtVatRate vrtVatRate = VrtVatRateMapper.INSTANCE.convertToVrtVatRate(vrtVatRateUpdateRequestDto);

        vrtVatRateValidationService.controlIsVatRateNegative(vrtVatRate);
        vrtVatRateValidationService.controlIsProductTypeUnique(vrtVatRate);

        vrtVatRate = vrtVatRateEntityService.save(vrtVatRate);

        VrtVatRateDto vrtVatRateDto = VrtVatRateMapper.INSTANCE.convertToVrtVatRateDto(vrtVatRate);

        return vrtVatRateDto;
    }


    public void delete(Long id) {

        VrtVatRate vrtVatRate = vrtVatRateEntityService.getByIdWithControl(id);

        vrtVatRateEntityService.delete(vrtVatRate);

    }

}
