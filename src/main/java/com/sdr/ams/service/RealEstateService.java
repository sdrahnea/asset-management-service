package com.sdr.ams.service;

import com.sdr.ams.model.tangible.RealEstate;
import com.sdr.ams.repository.RealEstateRepository;
import org.springframework.stereotype.Service;

@Service
public class RealEstateService extends CoreEntityCrudService<RealEstate> {

    public RealEstateService(RealEstateRepository repository) {
        super(repository, "RealEstate");
    }
}

