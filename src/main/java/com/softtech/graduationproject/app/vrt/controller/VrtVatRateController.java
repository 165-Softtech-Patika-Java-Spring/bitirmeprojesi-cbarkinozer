package com.softtech.graduationproject.app.vrt.controller;

import com.softtech.graduationproject.app.gen.dto.RestResponse;
import com.softtech.graduationproject.app.vrt.dto.VrtVatRateDto;
import com.softtech.graduationproject.app.vrt.dto.VrtVatRateSaveRequestDto;
import com.softtech.graduationproject.app.vrt.dto.VrtVatRateUpdateRequestDto;
import com.softtech.graduationproject.app.vrt.service.VrtVatRateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/vat-rates")
public class VrtVatRateController {

    private final VrtVatRateService vrtVatRateService;

    @Operation(
            tags = "VAT Rate Controller",
            summary = "All VAT rates",
            description = "Gets all VAT rates."
    )
    @GetMapping
    public ResponseEntity findAll(){

        List<VrtVatRateDto> vrtVatRateDtoList = vrtVatRateService.findAll();

        return ResponseEntity.ok(RestResponse.of(vrtVatRateDtoList));
    }


    @Operation(
            tags="VAT Rate Controller",
            summary = "Save a VAT rate",
            description = "Saves a VAT rate."
    )
    @PostMapping("/save")
    public ResponseEntity save(VrtVatRateSaveRequestDto vrtVatRateSaveRequestDto){

        VrtVatRateDto vrtVatRateDto = vrtVatRateService.save(vrtVatRateSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(vrtVatRateSaveRequestDto));
    }


    @Operation(
            tags="VAT Rate Controller",
            summary = "Update a VAT Rate",
            description = "Updates a VAT RATE."
    )
    @PutMapping("/update")
    public ResponseEntity update(VrtVatRateUpdateRequestDto vrtVatRateUpdateRequestDto){

        VrtVatRateDto vrtVatRateDto = vrtVatRateService.update(vrtVatRateUpdateRequestDto);

        return ResponseEntity.ok(RestResponse.of(vrtVatRateDto));

    }


    @Operation(
            tags="VAT Rate Controller",
            summary = "Delete a VAT Rate",
            description = "Deletes a VAT RATE."
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){

        vrtVatRateService.delete(id);

        return ResponseEntity.ok(RestResponse.empty());
    }

}
