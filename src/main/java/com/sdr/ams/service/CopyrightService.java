package com.sdr.ams.service;

import com.sdr.ams.model.intangible.Copyright;
import com.sdr.ams.repository.CopyrightRepository;
import org.springframework.stereotype.Service;

@Service
public class CopyrightService extends CoreEntityCrudService<Copyright> {

    public CopyrightService(CopyrightRepository repository) {
        super(repository, "Copyright");
    }
}

