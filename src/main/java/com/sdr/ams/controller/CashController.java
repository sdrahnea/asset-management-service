package com.sdr.ams.controller;

import com.sdr.ams.model.tangible.Cash;
import com.sdr.ams.service.CashService;
import com.sdr.ams.service.ExportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cash")
public class CashController extends CoreEntityCrudController<Cash> {

    public CashController(CashService service, ExportService exportService) {
        super(service, exportService, "Cash", "Cash", "/cash");
    }

    @Override
    protected Cash createEntity() {
        return new Cash();
    }
}
