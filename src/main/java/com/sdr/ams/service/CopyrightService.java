package com.sdr.ams.service;

import com.sdr.ams.model.intangible.Copyright;
import com.sdr.ams.repository.CopyrightRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CopyrightService extends CoreEntityCrudService<Copyright> {

    private final CopyrightRepository repository;

    public CopyrightService(CopyrightRepository repository) {
        super(repository, "Copyright");
        this.repository = repository;
    }

    @Override
    public Copyright create(Copyright entity) {
        return repository.save(entity);
    }

    @Override
    public Copyright update(Long id, Copyright input) {
        Copyright existing = findById(id);
        BeanUtils.copyProperties(input, existing, "id", "createdAt", "createdBy", "updatedAt");
        return repository.save(existing);
    }
}

