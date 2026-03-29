package com.sdr.ams.repository;

import com.sdr.ams.model.tangible.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}

