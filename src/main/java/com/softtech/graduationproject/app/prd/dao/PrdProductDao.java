package com.softtech.graduationproject.app.prd.dao;

import com.softtech.graduationproject.app.prd.entity.PrdProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PrdProductDao extends JpaRepository<PrdProduct,Long> {
    List<PrdProduct> getAllByVatFreePriceBetween(BigDecimal min, BigDecimal max);
}
