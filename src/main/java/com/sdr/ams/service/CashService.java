package com.sdr.ams.service;

import com.sdr.ams.model.tangible.Cash;
import com.sdr.ams.repository.CashRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CashService extends CoreEntityCrudService<Cash> {

    private final CashRepository repository;

    public CashService(CashRepository repository) {
        super(repository, "Cash");
        this.repository = repository;
    }

    @Override
    public Cash create(Cash entity) {
        return repository.save(entity);
    }

    @Override
    public Cash update(Long id, Cash input) {
        Cash existing = findById(id);
        BeanUtils.copyProperties(input, existing, "id", "createdAt", "createdBy", "updatedAt");
        return repository.save(existing);
    }
}

