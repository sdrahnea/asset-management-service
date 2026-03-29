package com.sdr.ams.service;

import com.sdr.ams.model.financial.Bond;
import com.sdr.ams.repository.BondRepository;
import org.springframework.stereotype.Service;

@Service
public class BondService extends CoreEntityCrudService<Bond> {

    public BondService(BondRepository repository) {
        super(repository, "Bond");
    }
}

