package com.sdr.ams.controller;

import com.sdr.ams.model.tangible.Cash;
import com.sdr.ams.service.CashService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cash")
public class CashController extends CoreEntityCrudController<Cash> {

    public CashController(CashService service) {
        super(service, "Cash", "Cash", "/cash");
    }

    @Override
    protected Cash createEntity() {
        return new Cash();
    }
}

