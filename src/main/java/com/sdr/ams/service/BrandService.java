package com.sdr.ams.service;

import com.sdr.ams.model.intangible.Brand;
import com.sdr.ams.repository.BrandRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BrandService extends CoreEntityCrudService<Brand> {

    private final BrandRepository repository;

    public BrandService(BrandRepository repository) {
        super(repository, "Brand");
        this.repository = repository;
    }

    @Override
    public Brand create(Brand entity) {
        return repository.save(entity);
    }

    @Override
    public Brand update(Long id, Brand input) {
        Brand existing = findById(id);
        BeanUtils.copyProperties(input, existing, "id", "createdAt", "createdBy", "updatedAt");
        return repository.save(existing);
    }
}

