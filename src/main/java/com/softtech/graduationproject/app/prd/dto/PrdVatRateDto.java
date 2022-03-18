package com.softtech.graduationproject.app.prd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrdVatRateDto {

    Long id;
    private Integer vatRate;
}
