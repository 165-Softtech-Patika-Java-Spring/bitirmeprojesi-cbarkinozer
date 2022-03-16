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

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PrdProductService {

    private final PrdProductEntityService prdProductEntityService;

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

    public PrdProductDto save(PrdProductSaveRequestDto prdProductSaveRequestDto) {

        PrdProduct prdProduct = PrdProductMapper.INSTANCE.convertToPrdProduct(prdProductSaveRequestDto);

        prdProduct = prdProductEntityService.save(prdProduct);

        PrdProductDto prdProductDto = PrdProductMapper.INSTANCE.convertToPrdProductDto(prdProduct);

        return prdProductDto;
    }


    public PrdProductDto update(PrdProductUpdateRequestDto prdProductUpdateRequestDto) {

        Long id = prdProductUpdateRequestDto.getId();
        controlIsPrdProductExist(id);

        PrdProduct prdProduct = PrdProductMapper.INSTANCE.convertToPrdProduct(prdProductUpdateRequestDto);

        prdProduct = prdProductEntityService.save(prdProduct);

        PrdProductDto prdProductDto = PrdProductMapper.INSTANCE.convertToPrdProductDto(prdProduct);

        return prdProductDto;
    }

    private void controlIsPrdProductExist(Long id){

        boolean isExist = prdProductEntityService.existsById(id);
        if (!isExist){
            throw new ItemNotFoundException(PrdErrorMessage.PRODUCT_NOT_FOUND);
        }

    }

    public void delete(Long id) {
        PrdProduct prdProduct = prdProductEntityService.getByIdWithControl(id);
        prdProductEntityService.delete(prdProduct);
    }
}
