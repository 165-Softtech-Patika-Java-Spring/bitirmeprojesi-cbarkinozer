package com.softtech.graduationproject.app.prd.service;

import com.softtech.graduationproject.app.gen.exceptions.IllegalFieldException;
import com.softtech.graduationproject.app.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.app.prd.dto.PrdProductAnalysisRequestDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductSaveRequestDto;
import com.softtech.graduationproject.app.prd.dto.PrdProductUpdateRequestDto;
import com.softtech.graduationproject.app.prd.entity.PrdProduct;
import com.softtech.graduationproject.app.prd.service.entityservice.PrdProductEntityService;
import com.softtech.graduationproject.app.vrt.entity.VrtVatRate;
import com.softtech.graduationproject.app.vrt.service.entityservice.VrtVatRateEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Mock
    private PrdProductUtilityService prdProductUtilityService;


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

        doNothing().when(prdProductValidationService).controlIsParameterMinIsLargerThanMax(min,max);
        when(prdProductEntityService.findProductsByPriceInterval(min,max)).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findProductsByPriceInterval(min,max);

        assertEquals(2, result.size());
    }


    @Test
    void findProductsByPriceInterval_WhenProductList_IsEmpty() {

        List<PrdProduct> prdProductList = new ArrayList<>();

        BigDecimal min = BigDecimal.valueOf(1);
        BigDecimal max = BigDecimal.valueOf(2);

        doNothing().when(prdProductValidationService).controlIsParameterMinIsLargerThanMax(min,max);
        when(prdProductEntityService.findProductsByPriceInterval(min,max)).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findProductsByPriceInterval(min,max);

        assertEquals(0, result.size());
    }

    @Test
    void dontFindProductsByPriceInterval_WhenParameterMin_IsLargerThanMax() {

        BigDecimal min = BigDecimal.valueOf(2);
        BigDecimal max = BigDecimal.valueOf(1);

        doThrow(IllegalFieldException.class).when(prdProductValidationService).controlIsParameterMinIsLargerThanMax(min,max);

        assertThrows(IllegalFieldException.class, () -> prdProductService.findProductsByPriceInterval(min,max));
    }

    @Test
    void findProductsByPriceInterval_WhenParameterMinAndMax_AreEqual() {

        PrdProduct prdProduct = mock(PrdProduct.class);
        List<PrdProduct> prdProductList = new ArrayList<>();

        prdProductList.add(prdProduct);
        prdProductList.add(prdProduct);

        BigDecimal min = BigDecimal.valueOf(1);
        BigDecimal max = BigDecimal.valueOf(1);

        doNothing().when(prdProductValidationService).controlIsParameterMinIsLargerThanMax(min,max);
        when(prdProductEntityService.findProductsByPriceInterval(min,max)).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findProductsByPriceInterval(min,max);

        assertEquals(2, result.size());

    }

    @Test
    void findProductsByProductType() {

        PrdProduct prdProduct = mock(PrdProduct.class);
        List<PrdProduct> prdProductList = new ArrayList<>();
        prdProductList.add(prdProduct);

        VrtVatRate vrtVatRate = mock(VrtVatRate.class);

        when(vrtVatRateEntityService.findVatRatesByProductType(vrtVatRate.getProductType()))
                .thenReturn(Optional.of(vrtVatRate));

        when(prdProductEntityService.findProductsByVatRateId(vrtVatRate.getId())).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findProductsByProductType(vrtVatRate.getProductType());

        assertEquals(1, result.size());
    }

    @Test
    void findProductsByProductType_WhenProductList_IsEmpty() {

        List<PrdProduct> prdProductList = new ArrayList<>();

        VrtVatRate vrtVatRate = mock(VrtVatRate.class);

        when(vrtVatRateEntityService.findVatRatesByProductType(vrtVatRate.getProductType()))
                .thenReturn(Optional.of(vrtVatRate));

        when(prdProductEntityService.findProductsByVatRateId(vrtVatRate.getId())).thenReturn(prdProductList);

        List<PrdProductDto> result = prdProductService.findProductsByProductType(vrtVatRate.getProductType());

        assertEquals(0, result.size());
    }


    @Test
    void getProductAnalysis() {
        /*

        PrdProductAnalysisRequestDto prdProductAnalysisRequestDto = mock(PrdProductAnalysisRequestDto.class);

        List<VrtVatRate> vrtVatRateList = new ArrayList<>();
        VrtVatRate vrtVatRate = mock(VrtVatRate.class);
        vrtVatRateList.add(vrtVatRate);

        when(vrtVatRateEntityService.findAll()).thenReturn(vrtVatRateList);

        when(vrtVatRate.getId()).thenReturn(1L);

        when(prdProductEntityService.getProductAnalysis(1L)).thenReturn(prdProductAnalysisRequestDto);

        assertEquals(vrtVatRate.getProductType(),prdProductAnalysisRequestDto.getProductType());

         */
    }


    @Test
    void saveProduct() {

        PrdProductSaveRequestDto prdProductSaveRequestDto = mock(PrdProductSaveRequestDto.class);

        PrdProduct prdProduct = mock(PrdProduct.class);

        when(prdProduct.getId()).thenReturn(1L);

        when(prdProductEntityService.save(any())).thenReturn(prdProduct);

        PrdProductDto result = prdProductService.saveProduct(prdProductSaveRequestDto);

        assertEquals(1L, result.getId());
    }

    @Test
    void dontSaveProduct_WhenParameter_IsNull() {

        PrdProductSaveRequestDto prdProductSaveRequestDto = null;

        doThrow(IllegalFieldException.class).when(prdProductValidationService)
                                            .controlIsParameterNull(prdProductSaveRequestDto);

        assertThrows(IllegalFieldException.class, () -> prdProductService.saveProduct(null));
    }

    @Test
    void dontSaveProduct_WhenFields_AreNull(){

        PrdProductSaveRequestDto prdProductSaveRequestDto = mock(PrdProductSaveRequestDto.class);
        PrdProduct prdProduct = mock(PrdProduct.class);

        doNothing().when(prdProductValidationService).controlIsParameterNull(prdProductSaveRequestDto);

        doThrow(IllegalFieldException.class).when(prdProductValidationService)
                .controlAreFieldsNonNull(prdProduct);

        assertThrows(IllegalFieldException.class, () -> prdProductService.saveProduct(null));

        verify(prdProductValidationService).controlAreFieldsNonNull(prdProduct);
    }

    @Test
    void dontSaveProduct_WhenPrice_IsNotPositive(){


        PrdProductSaveRequestDto prdProductSaveRequestDto = mock(PrdProductSaveRequestDto.class);
        PrdProduct prdProduct = mock(PrdProduct.class);

        BigDecimal price = BigDecimal.valueOf(-1);
        when(prdProduct.getVatFreePrice()).thenReturn(price);

        doNothing().when(prdProductValidationService).controlIsParameterNull(prdProductSaveRequestDto);

        doThrow(IllegalFieldException.class).when(prdProductValidationService)
                .controlAreFieldsNonNull(prdProduct);

        assertThrows(IllegalFieldException.class, () -> prdProductService.saveProduct(null));

        verify(prdProductValidationService).controlAreFieldsNonNull(prdProduct);


    }

    @Test
    void updateProduct() {

        Long id = 1L;

        PrdProductUpdateRequestDto prdProductUpdateRequestDto = mock(PrdProductUpdateRequestDto.class);
        PrdProduct prdProduct = mock(PrdProduct.class);
        when(prdProduct.getId()).thenReturn(id);

        doNothing().when(prdProductValidationService).controlIsPrdProductExist(anyLong());

        when(prdProductEntityService.save(any())).thenReturn(prdProduct);

        PrdProductDto prdProductDto = prdProductService.updateProduct(prdProductUpdateRequestDto);

        assertEquals(id, prdProductDto.getId());
    }

    @Test
    void dontUpdateProduct_WhenProduct_DoesNotExist() {

        PrdProductUpdateRequestDto prdProductUpdateRequestDto = mock(PrdProductUpdateRequestDto.class);
        PrdProduct prdProduct = mock(PrdProduct.class);

        doNothing().when(prdProductValidationService).controlIsPrdProductExist(anyLong());

        doThrow(IllegalFieldException.class).when(prdProductValidationService).controlAreFieldsNonNull(prdProduct);

        assertThrows(IllegalFieldException.class, () -> prdProductService.updateProduct(prdProductUpdateRequestDto));

        verify(prdProductValidationService).controlIsPrdProductExist(anyLong());
    }

    @Test
    void dontUpdateProduct_WhenFields_AreNull(){

        PrdProductUpdateRequestDto prdProductUpdateRequestDto = mock(PrdProductUpdateRequestDto.class);

        doThrow(ItemNotFoundException.class).when(prdProductValidationService).controlIsPrdProductExist(anyLong());

        assertThrows(ItemNotFoundException.class, () -> prdProductService.updateProduct(prdProductUpdateRequestDto));

        verify(prdProductValidationService).controlIsPrdProductExist(anyLong());

    }

    @Test
    void dontUpdateProduct_WhenPrice_IsNotPositive(){

        PrdProductUpdateRequestDto prdProductUpdateRequestDto = mock(PrdProductUpdateRequestDto.class);

        PrdProduct prdProduct = mock(PrdProduct.class);

        BigDecimal price = BigDecimal.valueOf(-1);
        when(prdProduct.getVatFreePrice()).thenReturn(price);


        doNothing().when(prdProductValidationService).controlIsPrdProductExist(anyLong());

        when(prdProductUtilityService.calculatePriceWithControl(prdProduct)).thenThrow(IllegalFieldException.class);

        assertThrows(IllegalFieldException.class, () -> prdProductService.updateProduct(prdProductUpdateRequestDto));

        verify(prdProductValidationService).controlIsPrdProductExist(anyLong());

    }

    @Test
    void batchProductUpdate() {

        Long vrtVatRateId = 1L;

        PrdProduct prdProduct = mock(PrdProduct.class);
        List<PrdProduct> prdProductList = new ArrayList<>();
        prdProductList.add(prdProduct);

        doNothing().when(prdProductValidationService).controlIsParameterNull(vrtVatRateId);

        when(prdProductEntityService.findProductsByVatRateId(vrtVatRateId)).thenReturn(prdProductList);

        List<PrdProduct> productList = prdProductEntityService.findProductsByVatRateId(vrtVatRateId);

        assertEquals(vrtVatRateId,productList.get(0).getVrtVatRateId());

        doNothing().when(prdProductValidationService).controlIsListNull(productList);

        verify(prdProductEntityService).save(prdProduct);

    }

    @Test
    void dontBatchProductUpdate_WhenProduct_DoesNotExist() {

        List<PrdProduct> prdProductList = new ArrayList<>();

        doNothing().when(prdProductValidationService).controlIsParameterNull(anyLong());

        when(prdProductEntityService.findProductsByVatRateId(anyLong())).thenReturn(prdProductList);

        doThrow(ItemNotFoundException.class).when(prdProductValidationService).controlIsListNull(prdProductList);

        assertThrows(ItemNotFoundException.class, () -> prdProductService.batchProductUpdate(anyLong()));

        verify(prdProductValidationService).controlIsParameterNull(anyLong());
        verify(prdProductEntityService).findProductsByVatRateId(anyLong());
    }

    @Test
    void dontBatchProductUpdate_WhenPrice_IsNotPositive(){

        Long vrtVatRateId = 1L;
        BigDecimal price = BigDecimal.valueOf(-1);

        PrdProduct prdProduct = mock(PrdProduct.class);
        List<PrdProduct> prdProductList = new ArrayList<>();
        prdProductList.add(prdProduct);

        doNothing().when(prdProductValidationService).controlIsParameterNull(vrtVatRateId);

        when(prdProductEntityService.findProductsByVatRateId(vrtVatRateId)).thenReturn(prdProductList);

        doNothing().when(prdProductValidationService).controlIsParameterNull(vrtVatRateId);

        doThrow(IllegalFieldException.class).when(prdProductValidationService)
                .controlIsPricePositive(price);

        assertThrows(IllegalFieldException.class, () -> prdProductService.batchProductUpdate(anyLong()));

        verify(prdProductValidationService).controlIsPricePositive(price);
    }


    @Test
    void deleteProduct() {

        PrdProduct prdProduct = mock(PrdProduct.class);

        when(prdProductEntityService.getByIdWithControl(anyLong())).thenReturn(prdProduct);

        prdProductService.deleteProduct(anyLong());

        verify(prdProductEntityService).getByIdWithControl(anyLong());
        verify(prdProductEntityService).delete(any());
    }

    @Test
    void dontDeleteProduct_WhenId_DoesNotExist(){

        when(prdProductEntityService.getByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> prdProductService.deleteProduct(anyLong()));

        verify(prdProductEntityService).getByIdWithControl(anyLong());
    }

}