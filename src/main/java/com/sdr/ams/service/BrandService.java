package com.sdr.ams.service;

import com.sdr.ams.model.intangible.Brand;
import com.sdr.ams.repository.BrandRepository;
import org.springframework.stereotype.Service;

@Service
public class BrandService extends CoreEntityCrudService<Brand> {

    public BrandService(BrandRepository repository) {
        super(repository, "Brand");
    }
}

