package com.sdr.ams.repository;

import com.sdr.ams.model.tangible.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {
}

