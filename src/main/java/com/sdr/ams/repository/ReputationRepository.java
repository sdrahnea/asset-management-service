package com.sdr.ams.repository;

import com.sdr.ams.model.intangible.Reputation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReputationRepository extends JpaRepository<Reputation, Long> {
}

