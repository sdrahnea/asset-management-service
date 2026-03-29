package com.sdr.ams.repository;

import com.sdr.ams.model.tangible.Cash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashRepository extends JpaRepository<Cash, Long> {
}

