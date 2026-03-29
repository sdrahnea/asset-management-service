package com.sdr.ams.service;

import com.sdr.ams.model.tangible.Inventory;
import com.sdr.ams.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService extends CoreEntityCrudService<Inventory> {

    public InventoryService(InventoryRepository repository) {
        super(repository, "Inventory");
    }
}

