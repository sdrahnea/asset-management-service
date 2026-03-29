package com.sdr.ams.repository;

import com.sdr.ams.model.intangible.Patent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatentRepository extends JpaRepository<Patent, Long> {
}

