package com.sdr.ams.repository;

import com.sdr.ams.model.financial.Bond;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BondRepository extends JpaRepository<Bond, Long> {
}

