package com.sdr.ams.service;

import com.sdr.ams.model.intangible.Reputation;
import com.sdr.ams.repository.ReputationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReputationService extends CoreEntityCrudService<Reputation> {

    public ReputationService(ReputationRepository repository) {
        super(repository, "Reputation");
    }
}

