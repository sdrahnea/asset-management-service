package com.sdr.ams.service;

import com.sdr.ams.model.financial.Stock;
import com.sdr.ams.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService extends CoreEntityCrudService<Stock> {

    public StockService(StockRepository repository) {
        super(repository, "Stock");
    }
}

