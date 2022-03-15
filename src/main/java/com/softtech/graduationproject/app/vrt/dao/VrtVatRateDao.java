package com.softtech.graduationproject.app.vrt.dao;

import com.softtech.graduationproject.app.vrt.entity.VrtVatRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VrtVatRateDao extends JpaRepository<VrtVatRate,Long> {
}
