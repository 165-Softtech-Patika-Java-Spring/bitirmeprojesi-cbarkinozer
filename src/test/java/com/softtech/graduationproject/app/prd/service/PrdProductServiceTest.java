package com.softtech.graduationproject.app.prd.service;

import com.softtech.graduationproject.app.gen.exceptions.IllegalFieldException;
import com.softtech.graduationproject.app.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.app.prd.dto.PrdProductDto;
import com.softtech.graduationproject.app.prd.entity.PrdProduct;
import com.softtech.graduationproject.app.prd.service.entityservice.PrdProductEntityService;
import com.softtech.graduationproject.app.vrt.service.entityservice.VrtVatRateEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrdProductServiceTest {

    @InjectMocks
    private PrdProductService prdProductService;

    @Mock
    private PrdProductEntityService prdProductEntityService;

    @Mock
    private VrtVatRateEntityService vrtVatRateEntityService;

    @Mock
    private PrdProductValidationService prdProductValidationService;


    @Test
    void findAllProducts() {

        PrdProduct prdProduct = mock(PrdProduct.class);
        List<PrdProduct> prdProductList = new ArrayList<>();
        prdProductList.add(prdProduct);

        when(prdProductEntityService.findAll()).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findAllProducts();

        assertEquals(1, result.size());
    }

    @Test
    void findAllProducts_WhenProductList_IsEmpty() {

        List<PrdProduct> prdProductList = new ArrayList<>();

        when(prdProductEntityService.findAll()).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findAllProducts();

        assertEquals(0, result.size());
    }


    @Test
    void findProductById(){

        Long id = 1L;

        PrdProduct prdProduct = mock(PrdProduct.class);
        when(prdProduct.getId()).thenReturn(id);

        when(prdProductEntityService.getByIdWithControl(id)).thenReturn(prdProduct);

        PrdProductDto prdProductDto = prdProductService.findProductById(id);

        assertEquals(id, prdProductDto.getId());
    }


    @Test
    void dontFindProductById_WhenId_DoesNotExist(){

        when(prdProductEntityService.getByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> prdProductService.findProductById(anyLong()));

        verify(prdProductEntityService).getByIdWithControl(anyLong());
    }


    @Test
    void findProductsByPriceInterval() {

        PrdProduct prdProduct = mock(PrdProduct.class);
        List<PrdProduct> prdProductList = new ArrayList<>();

        prdProductList.add(prdProduct);
        prdProductList.add(prdProduct);

        BigDecimal min = BigDecimal.valueOf(1);
        BigDecimal max = BigDecimal.valueOf(2);

        when(prdProductEntityService.findProductsByPriceInterval(min,max)).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findProductsByPriceInterval(min,max);

        assertEquals(2, result.size());
    }


    @Test
    void findProductsByPriceInterval_WhenProductList_IsEmpty() {

        List<PrdProduct> prdProductList = new ArrayList<>();

        BigDecimal min = BigDecimal.valueOf(1);
        BigDecimal max = BigDecimal.valueOf(2);

        when(prdProductEntityService.findProductsByPriceInterval(min,max)).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findProductsByPriceInterval(min,max);

        assertEquals(0, result.size());
    }

    @Test
    void dontFindProductsByPriceInterval_WhenParameterMin_IsLargerThanMax() {

        BigDecimal min = BigDecimal.valueOf(2);
        BigDecimal max = BigDecimal.valueOf(1);

        when(prdProductEntityService.findProductsByPriceInterval(min,max)).thenThrow(IllegalFieldException.class);

        assertThrows(IllegalFieldException.class, () -> prdProductService.findProductsByPriceInterval(min,max));

        verify(prdProductEntityService).findProductsByPriceInterval(min,max);
    }

    @Test
    void findProductsByPriceInterval_WhenParameterMinAndMax_AreEqual() {

        PrdProduct prdProduct = mock(PrdProduct.class);
        List<PrdProduct> prdProductList = new ArrayList<>();

        prdProductList.add(prdProduct);
        prdProductList.add(prdProduct);

        BigDecimal min = BigDecimal.valueOf(1);
        BigDecimal max = BigDecimal.valueOf(1);

        when(prdProductEntityService.findProductsByPriceInterval(min,max)).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findProductsByPriceInterval(min,max);

        assertEquals(2, result.size());

    }

    @Test
    void shouldFindProductsByProductType() {
    }

    @Test
    void shouldFindProductsByProductType_WhenProductList_IsEmpty() {
    }


    @Test
    void shouldGetProductAnalysis() {
    }

    @Test
    void shouldGetProductAnalysis_WhenProductList_IsEmpty() {
    }

    @Test
    void shouldSaveProduct() {
    }

    @Test
    void shouldSaveProduct_WhenParameter_IsNull() {
    }

    @Test
    void shouldUpdateProduct() {
    }

    @Test
    void shouldBatchProductUpdate() {
    }

    @Test
    void shouldDeleteProduct() {
    }
}