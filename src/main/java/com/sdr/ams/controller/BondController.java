package com.sdr.ams.controller;

import com.sdr.ams.model.financial.Bond;
import com.sdr.ams.service.BondService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/bonds")
public class BondController extends CoreEntityCrudController<Bond> {

    public BondController(BondService service) {
        super(service, "Bond", "Bonds", "/bonds");
    }

    @Override
    protected Bond createEntity() {
        return new Bond();
    }
}

