package com.softtech.graduationproject.app.prd.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name="PRD_PRODUCT")
public class PrdProduct {

    @Id
    @SequenceGenerator(name = "PrdProduct" , sequenceName = "PRD_PRODUCT_ID_SEQ")
    @GeneratedValue(generator = "PrdProduct")
    private Long id;

    @Column(name="ID_USR_USER",nullable = false)
    private Long usrUserId;

    @Column(name="ID_VRT_VAT_RATE",nullable = false)
    private Long vrtVatRateId;

    @Column(name="NAME",length=100, nullable = false)
    private String name;

    @Column(name="VAT_FREE_PRICE", precision = 19, scale = 2)
    private BigDecimal vatFreePrice;

}
