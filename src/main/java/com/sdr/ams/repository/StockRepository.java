package com.sdr.ams.repository;

import com.sdr.ams.model.financial.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}

