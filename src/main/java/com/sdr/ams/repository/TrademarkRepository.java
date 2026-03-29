package com.sdr.ams.repository;

import com.sdr.ams.model.intangible.Trademark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrademarkRepository extends JpaRepository<Trademark, Long> {
}

