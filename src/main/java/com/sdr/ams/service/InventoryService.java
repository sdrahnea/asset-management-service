package com.sdr.ams.service;

import com.sdr.ams.model.tangible.Inventory;
import com.sdr.ams.repository.InventoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class InventoryService extends CoreEntityCrudService<Inventory> {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        super(repository, "Inventory");
        this.repository = repository;
    }

    @Override
    public Inventory create(Inventory entity) {
        return repository.save(entity);
    }

    @Override
    public Inventory update(Long id, Inventory input) {
        Inventory existing = findById(id);
        BeanUtils.copyProperties(input, existing, "id", "createdAt", "createdBy", "updatedAt");
        return repository.save(existing);
    }
}

