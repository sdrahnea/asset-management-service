package com.sdr.ams.service;

import com.sdr.ams.model.tangible.Machinery;
import com.sdr.ams.repository.MachineryRepository;
import org.springframework.stereotype.Service;

@Service
public class MachineryService extends CoreEntityCrudService<Machinery> {

    public MachineryService(MachineryRepository repository) {
        super(repository, "Machinery");
    }
}

