package com.softtech.graduationproject.app.prd.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PrdProductUpdateRequestDto {

    private Long id;
    private Long usrUserId;
    private Long vrtVatRateId;
    private String name;
    private BigDecimal vatFreePrice;
}
