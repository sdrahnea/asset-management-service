package com.sdr.ams.repository;

import com.sdr.ams.model.tangible.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}

