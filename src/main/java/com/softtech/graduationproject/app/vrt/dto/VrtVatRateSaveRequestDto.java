package com.softtech.graduationproject.app.vrt.dto;

import com.softtech.graduationproject.app.vrt.enums.VrtProductType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VrtVatRateSaveRequestDto {

    private VrtProductType productType;
    private Integer vatRate;

}
