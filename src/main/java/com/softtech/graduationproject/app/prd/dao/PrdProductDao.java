package com.softtech.graduationproject.app.prd.dao;

import com.softtech.graduationproject.app.prd.dto.PrdVatRateDto;
import com.softtech.graduationproject.app.prd.entity.PrdProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrdProductDao extends JpaRepository<PrdProduct,Long> {

    List<PrdProduct> getAllByVatFreePriceBetween(BigDecimal min, BigDecimal max);

    @Query(
            " select new com.softtech.graduationproject.app.prd.dto.PrdVatRateDto( " +
                    "vrt.vatRate " +
                    " ) " +
                    " from PrdProduct prd left join VrtVatRate vrt on prd.vrtVatRateId = vrt.id " +
                    " left join VrtVatRate  vrtVatRate on prd.vrtVatRateId = vrt.id " +
                    "where prd.vrtVatRateId = :vatRateId "
    )
    Optional<PrdVatRateDto> getVatRateByVatRateId(@Param("vatRateId") Long vatRateId);

}
