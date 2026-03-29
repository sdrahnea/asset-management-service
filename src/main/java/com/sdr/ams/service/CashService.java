package com.sdr.ams.service;

import com.sdr.ams.model.tangible.Cash;
import com.sdr.ams.repository.CashRepository;
import org.springframework.stereotype.Service;

@Service
public class CashService extends CoreEntityCrudService<Cash> {

    public CashService(CashRepository repository) {
        super(repository, "Cash");
    }
}

