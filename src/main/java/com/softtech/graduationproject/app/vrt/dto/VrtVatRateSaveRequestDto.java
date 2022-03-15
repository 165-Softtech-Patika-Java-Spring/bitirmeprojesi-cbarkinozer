package com.softtech.graduationproject.app.vrt.dto;

import com.softtech.graduationproject.app.vrt.enums.VrtProductType;
import lombok.Data;

@Data
public class VrtVatRateSaveRequestDto {

    private VrtProductType productType;
    private int vatRate;

}
