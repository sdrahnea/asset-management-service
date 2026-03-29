package com.sdr.ams.controller;

import com.sdr.ams.model.intangible.Trademark;
import com.sdr.ams.service.TrademarkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trademarks")
public class TrademarkController extends CoreEntityCrudController<Trademark> {

    public TrademarkController(TrademarkService service) {
        super(service, "Trademark", "Trademarks", "/trademarks");
    }

    @Override
    protected Trademark createEntity() {
        return new Trademark();
    }
}

