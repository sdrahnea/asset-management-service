package com.sdr.ams.controller;

import com.sdr.ams.model.financial.Stock;
import com.sdr.ams.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stocks")
public class StockController extends CoreEntityCrudController<Stock> {

    public StockController(StockService service) {
        super(service, "Stock", "Stocks", "/stocks");
    }

    @Override
    protected Stock createEntity() {
        return new Stock();
    }
}

