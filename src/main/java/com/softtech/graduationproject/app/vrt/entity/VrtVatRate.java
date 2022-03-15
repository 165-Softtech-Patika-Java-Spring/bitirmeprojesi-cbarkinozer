package com.softtech.graduationproject.app.vrt.entity;

import com.softtech.graduationproject.app.vrt.enums.VrtProductType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="VRT_VAT_RATE")
public class VrtVatRate {

    @Id
    @SequenceGenerator(name = "VrtVatRate" , sequenceName = "VRT_VAT_RATE_ID_SEQ")
    @GeneratedValue(generator = "VrtVatRate")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="PRODUCT_TYPE",length=30)
    private VrtProductType productType;

    @Column(name="VAT_RATE",nullable = false)
    private int vatRate;

}
