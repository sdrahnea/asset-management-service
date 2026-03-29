package com.sdr.ams.service;

import com.sdr.ams.model.intangible.Patent;
import com.sdr.ams.repository.PatentRepository;
import org.springframework.stereotype.Service;

@Service
public class PatentService extends CoreEntityCrudService<Patent> {

    public PatentService(PatentRepository repository) {
        super(repository, "Patent");
    }
}

