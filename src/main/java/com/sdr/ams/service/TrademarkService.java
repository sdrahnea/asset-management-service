package com.sdr.ams.service;

import com.sdr.ams.model.intangible.Trademark;
import com.sdr.ams.repository.TrademarkRepository;
import org.springframework.stereotype.Service;

@Service
public class TrademarkService extends CoreEntityCrudService<Trademark> {

    public TrademarkService(TrademarkRepository repository) {
        super(repository, "Trademark");
    }
}

